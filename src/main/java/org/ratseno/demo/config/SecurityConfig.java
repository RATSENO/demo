package org.ratseno.demo.config;

import java.util.Arrays;

import org.ratseno.demo.common.security.entrypoint.RestAuthenticationEntryPoint;
import org.ratseno.demo.common.security.handler.CustomAccessDeniedHandler;
import org.ratseno.demo.common.security.jwt.filter.JwtAuthenticationFilter;
import org.ratseno.demo.common.security.jwt.filter.JwtRequestFilter;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.ratseno.demo.common.security.service.CustomUserDetailService;
import org.ratseno.demo.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
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

    private static final String[] SWAGGER3_URL = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**"};

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisUtil redisUtil;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, RedisUtil redisUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("SecurityConfig...");
        // 폼 로그인 기능, 베이직 인증 비활성화
        http.formLogin().disable()
                .httpBasic().disable();

        http.cors();

        // CSRF 방지 지원 기능 비활성화
        http.csrf().disable();
        http.addFilterAt(new JwtAuthenticationFilter(authenticationManager(), this.jwtTokenProvider, this.redisUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRequestFilter(this.jwtTokenProvider),UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers().hasAnyRole("ADMIN").antMatchers("/").permitAll()
                .antMatchers("/codes/**").access("permitAll")
                .antMatchers("/users/**").access("permitAll")
                .antMatchers("/auth/**").access("permitAll")
                .antMatchers("/resttemplate/**").access("permitAll")
                .antMatchers("/webclient/**").access("permitAll")
                .antMatchers( "/codegroups/**").access("hasRole('ADMIN')")
                .antMatchers("/codedetails/**").access("hasRole('ADMIN')")
                // .antMatchers("/boards/**")
                // .access("request.method == 'GET' ? permitAll : hasAnyRole('MEMBER','ADMIN')")
                // .antMatchers("/notices/**")
                // .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                // .antMatchers("/items/**")
                // .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                // .antMatchers("/coins/**").access("hasRole('MEMBER')")
                // .antMatchers("/useritems/**").access("hasAnyRole('MEMBER', 'ADMIN')")
                // .antMatchers("/pds/**")
                // .access("request.method == 'GET' ? permitAll : hasRole('ADMIN')")
                .antMatchers(SWAGGER3_URL).access("permitAll")
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

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
