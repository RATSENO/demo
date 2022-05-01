package org.ratseno.demo.common.security.jwt.filter;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
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

    public JwtAuthenticationFilter(AuthenticationManager manager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = manager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/authenticate");
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("==========JwtAuthenticationFilter.attemptAuthentication========");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
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

        String token = this.jwtTokenProvider.createToken(userNo, userId, roles);
        response.addHeader("Authorization", "Bearer " + token);
    }

}
