package org.ratseno.demo.common.security.jwt.provider;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.domain.Member;
import org.ratseno.demo.prop.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final AppProperties appProperties;

    public static final long ACCESS_TOKEN_EXPIRED_SECOND = 60L;
    public static final long REFRESH_TOKEN_EXPIRED_SECOND = 1000L * 60 * 24 * 2;

    public static final String ACCESS_TOKEN = "X-ACCESS-TOKEN";
    public static final String REFRESH_TOKEN = "X-REFRESH-TOKEN";

    private byte[] getSigninKey() {
        return this.appProperties.getSecretKey().getBytes();
    }

    private boolean isEmpty(CharSequence cs) {
        return (cs == null || cs.length() == 0);
    }

    private boolean isNotEmpty(CharSequence cs) {
        return !this.isEmpty(cs);
    }

    public JwtTokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public Claims extractAllClaims(String jwtToken) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(getSigninKey()).parseClaimsJws(jwtToken).getBody();
    }

    public long getUserNo(String header) throws Exception {
        String token = header.substring(7);
        byte[] signinKey = this.getSigninKey();

        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signinKey).parseClaimsJws(token);

        String subject = ((Claims) parsedToken.getBody()).getSubject();
        long userNo = Long.parseLong(subject);

        return userNo;
    }

    /**
     * ACCESS TOKEN ??????
     * @param long userNo
     * @param String userId
     * @param List<String> roles
     * @return String
     */
    public String createAccessToken(long userNo, String userId, List<String> roles) {
        byte[] signinKey = getSigninKey();
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signinKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_SECOND))
                .claim("uno", "" + userNo)
                .claim("uid", userId)
                .claim("rol", roles)
                .compact();
        return token;
    }

    /**
     * REFRESH TOKEN ??????
     * @param long userNo
     * @param String userId
     * @param List<String> roles
     * @return String
     */
    public String createRefreshToken(long userNo, String userId, List<String> roles) {
        byte[] signinKey = getSigninKey();
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signinKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_SECOND))
                //.claim("uno", "" + userNo)
                //.claim("uid", userId)
                //.claim("rol", roles)
                .compact();
        return token;
    }    

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        String ACCESS_TOKEN = request.getHeader(JwtTokenProvider.ACCESS_TOKEN);
        String REFRESH_TOKEN = request.getHeader(JwtTokenProvider.REFRESH_TOKEN);

        if (this.isNotEmpty(ACCESS_TOKEN)) {
            try {
                byte[] signinKey = getSigninKey();
                Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signinKey)
                        .parseClaimsJws(ACCESS_TOKEN);

                Claims claims = (Claims) parsedToken.getBody();
                String userNo = (String) claims.get("uno");
                String userId = (String) claims.get("uid");

                List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) ((List) claims.get("rol"))
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if (this.isNotEmpty(userId)) {
                    Member member = new Member();
                    member.setUserNo(Long.valueOf(Long.parseLong(userNo)));
                    member.setUserId(userId);
                    member.setUserPw("");
                    CustomUser customUser = new CustomUser(member, authorities);
                    return new UsernamePasswordAuthenticationToken(customUser, null, authorities);
                }

            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());

            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());

            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());

            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());

            }
        }
        return null;
    }
}
