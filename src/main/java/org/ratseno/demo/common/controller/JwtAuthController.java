package org.ratseno.demo.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.common.security.jwt.constants.SecurityConstants;
import org.ratseno.demo.common.security.jwt.provider.JwtTokenProvider;
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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/auth"})
public class JwtAuthController {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager){
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
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

        String token = SecurityConstants.TOKEN_PREFIX + this.jwtTokenProvider.createToken(userNo, userId, roles);
        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
