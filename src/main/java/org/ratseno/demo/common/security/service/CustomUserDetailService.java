package org.ratseno.demo.common.security.service;

import org.ratseno.demo.common.security.domain.CustomUser;
import org.ratseno.demo.domain.Member;
import org.ratseno.demo.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService{
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:"+ username);
        Member member = memberRepository.findByUserId(username).get(0);
        log.info("member : "+ member);
        
        return (member == null) ? null : (UserDetails)new CustomUser(member);
    }
}
