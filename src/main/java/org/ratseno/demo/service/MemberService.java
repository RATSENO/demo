package org.ratseno.demo.service;

import org.ratseno.demo.domain.Member;

public interface MemberService {
    public void register(Member member) throws Exception;

    public Member read(Long userNo) throws Exception;

    public Long countAll() throws Exception;

    public void registerAdmin(Member member) throws Exception;
}
