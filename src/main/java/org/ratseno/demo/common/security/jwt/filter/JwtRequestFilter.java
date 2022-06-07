package org.ratseno.demo.common.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private boolean isEmpty(CharSequence cs) {
        return (cs == null || cs.length() == 0);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("==========JwtRequestFilter.doFilterInternal========");

        //요청 시 필터에 토큰 정보가 없을 경우
        String ACCESS_TOKEN = request.getHeader(JwtTokenProvider.ACCESS_TOKEN);
        String REFRESH_TOKEN = request.getHeader(JwtTokenProvider.REFRESH_TOKEN);
        if (isEmpty(ACCESS_TOKEN) || isEmpty(REFRESH_TOKEN)) {
            filterChain.doFilter((ServletRequest) request, (ServletResponse) response);
            return;
        }

        //필터에 토큰 정보가 있을 경우
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = this.jwtTokenProvider.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication((Authentication) usernamePasswordAuthenticationToken);
        filterChain.doFilter((ServletRequest) request, (ServletResponse) response);
    }
}
