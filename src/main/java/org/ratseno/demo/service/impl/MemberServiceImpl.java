package org.ratseno.demo.service.impl;

import org.ratseno.demo.domain.Member;
import org.ratseno.demo.domain.MemberAuth;
import org.ratseno.demo.repository.MemberRepository;
import org.ratseno.demo.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long countAll() throws Exception {
        return this.memberRepository.count();
    }

    @Override
    public Member read(Long userNo) throws Exception {
        return this.memberRepository.getById(userNo);
    }

    @Override
    public void register(Member member) throws Exception {
        Member memberEntity = new Member();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_MEMBER");

        memberEntity.addAuth(memberAuth);

        this.memberRepository.save(memberEntity);
        member.setUserNo(memberEntity.getUserNo());
    }

    @Override
    public void registerAdmin(Member member) throws Exception {
        Member memberEntity = new Member();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_ADMIN");

        memberEntity.addAuth(memberAuth);

        this.memberRepository.save(memberEntity);
    }

}
