package org.ratseno.demo.repository;

import java.util.List;

import org.ratseno.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserId(String paramString);
    
    @Query("SELECT m.userNo, m.userId, m.userPw, m.userName, m.regDate FROM Member m ORDER BY m.regDate DESC")
    List<Object[]> listAllMember();
  }