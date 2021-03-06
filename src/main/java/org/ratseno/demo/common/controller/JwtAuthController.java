package org.ratseno.demo.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
import org.ratseno.demo.common.util.RedisUtil;
import org.ratseno.demo.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/auth"})
public class JwtAuthController {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisUtil redisUtil;

    public JwtAuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, RedisUtil redisUtil){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.redisUtil = redisUtil;
    }


    @PostMapping(value = {"/token"})
    @ApiOperation(value = "JWT 토큰 발급")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Member member){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(member.getUserName(), member.getUserPw());
        Authentication authentication = this.authenticationManager.authenticate((Authentication) usernamePasswordAuthenticationToken);
        
        CustomUser user = (CustomUser) authentication.getPrincipal();
        long userNo = user.getUserNo();
        String userId = user.getUserId();
        List<String> roles = (List<String>) user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = this.jwtTokenProvider.createAccessToken(userNo, userId, roles);
        String refreshToken = this.jwtTokenProvider.createRefreshToken(userNo, userId, roles);
        Map<String, String> res = new HashMap<>();
        res.put(JwtTokenProvider.ACCESS_TOKEN, accessToken);
        res.put(JwtTokenProvider.REFRESH_TOKEN, refreshToken);

        redisUtil.setDataExpire(refreshToken, userId, 60L);

        return new ResponseEntity(res, HttpStatus.OK);
    }
}
