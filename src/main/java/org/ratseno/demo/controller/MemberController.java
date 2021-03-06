package org.ratseno.demo.controller;

import java.util.Locale;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.domain.Member;
import org.ratseno.demo.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping({ "/users" })
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<Member> register(@Validated @RequestBody Member member) throws Exception {
        log.info("=======MemberController.register=======");
        log.info("member.getUserName() = {}", member.getUserName());

        String inputPassword = member.getUserPw();
        member.setUserPw(this.passwordEncoder.encode(inputPassword));

        this.memberService.register(member);

        log.info("register member.getUserNo() = {}", member.getUserNo());
        log.info("=======MemberController.register.{}=======", HttpStatus.OK);
        return new ResponseEntity(member, HttpStatus.OK);
    }

    @PostMapping(value = { "/setup" }, produces = { "text/plain;charset=UTF-8" })
    public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member) throws Exception {
        log.info("=======MemberController.setupAdmin=======");
        log.info("setupAdmin : member.getUserName() = {}", member.getUserName());
        log.info("setupAdmin : service.countAll() = {}", this.memberService.countAll());

        if (this.memberService.countAll() == 0L) {
            String inputPassword = member.getUserPw();
            member.setUserPw(this.passwordEncoder.encode(inputPassword));
            this.memberService.registerAdmin(member);
            log.info("=======MemberController.setupAdmin.{}=======", HttpStatus.OK);
            return new ResponseEntity("SUCCESS", HttpStatus.OK);
        }
        String message = this.messageSource.getMessage("common.cannotSetupAdmin", null, Locale.KOREAN);
        log.info("=======MemberController.setupAdmin.{}=======", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping({ "/myinfo" })
    public ResponseEntity<Member> getMyInfo(@AuthenticationPrincipal @ApiIgnore CustomUser customUser) throws Exception {
        log.info("=======MemberController.getMyInfo=======");
        Long userNo = Long.valueOf(customUser.getUserNo());
        log.info("register userNo = {}", userNo);
        Member member = this.memberService.read(userNo);
        member.setUserPw("");

        log.info("=======MemberController.getMyInfo.{}=======", HttpStatus.OK);
        return new ResponseEntity(member, HttpStatus.OK);
    }


    /* @Operation(summary = "test hello", description = "hello api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("/hello")
     */    
}
