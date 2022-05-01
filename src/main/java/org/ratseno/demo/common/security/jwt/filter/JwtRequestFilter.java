package org.ratseno.demo.common.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtRequestFilter extends OncePerRequestFilter{
    private final JwtTokenProvider jwtTokenProvider;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private boolean isEmpty(CharSequence cs){
        return (cs == null || cs.length() ==0);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = this.jwtTokenProvider.getAuthentication(request);
        String header = request.getHeader("Authorization");
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        } 
        SecurityContextHolder.getContext().setAuthentication((Authentication)usernamePasswordAuthenticationToken);
        filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
    }
}
