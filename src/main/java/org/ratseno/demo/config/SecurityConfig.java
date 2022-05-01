package org.ratseno.demo.config;

import java.util.Arrays;

import org.ratseno.demo.common.security.entrypoint.RestAuthenticationEntryPoint;
import org.ratseno.demo.common.security.handler.CustomAccessDeniedHandler;
import org.ratseno.demo.common.security.jwt.filter.JwtAuthenticationFilter;
import org.ratseno.demo.common.security.jwt.filter.JwtRequestFilter;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.ratseno.demo.common.security.service.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("SecurityConfig...");

        http.formLogin().disable()
                .httpBasic().disable();

        http.cors();
        http.csrf().disable();
        http.addFilterAt(new JwtAuthenticationFilter(authenticationManager(), this.jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRequestFilter(this.jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers().permitAll()
                .requestMatchers().hasAnyRole("ADMIN").antMatchers("/").permitAll()
                .antMatchers(new String[] { "/codes/**" }).access("permitAll")
                .antMatchers(new String[] { "/users/**" }).access("permitAll")
                // .antMatchers(new String[] { "/codegroups/**" }).access("hasRole('ADMIN')")
                // .antMatchers(new String[] { "/codedetails/**" }).access("hasRole('ADMIN')")
                .antMatchers(new String[] { "/boards/**" })
                .access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER', 'ADMIN')")
                .antMatchers(new String[] { "/notices/**" })
                .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                .antMatchers(new String[] { "/items/**" })
                .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                .antMatchers(new String[] { "/coins/**" }).access("hasRole('MEMBER')")
                .antMatchers(new String[] { "/useritems/**" }).access("hasAnyRole('MEMBER', 'ADMIN')")
                .antMatchers(new String[] { "/pds/**" })
                .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.valueOf(true));
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setExposedHeaders(Arrays.asList(new String[] { "Authorization", "Content-Disposition" }));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return (CorsConfigurationSource) source;
    }
}
