package org.ratseno.demo.common.security.jwt.filter;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.ratseno.demo.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisUtil redisUtil;

    public JwtAuthenticationFilter(AuthenticationManager manager, JwtTokenProvider jwtTokenProvider, RedisUtil redisUtil) {
        this.authenticationManager = manager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisUtil = redisUtil;
        setFilterProcessesUrl("/api/authenticate");
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("==========JwtAuthenticationFilter.attemptAuthentication========");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate((Authentication) usernamePasswordAuthenticationToken);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authentication) {
        log.info("==========JwtAuthenticationFilter.successfulAuthentication========");

        CustomUser user = (CustomUser) authentication.getPrincipal();

        long userNo = user.getUserNo();
        String userId = user.getUserId();
        List<String> roles = (List<String>) user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = this.jwtTokenProvider.createAccessToken(userNo, userId, roles);
        String refreshToken = this.jwtTokenProvider.createRefreshToken(userNo, userId, roles);
        
        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, accessToken);
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, refreshToken);

        redisUtil.setDataExpire(refreshToken, userId, JwtTokenProvider.REFRESH_TOKEN_EXPIRED_SECOND);
    }

}
