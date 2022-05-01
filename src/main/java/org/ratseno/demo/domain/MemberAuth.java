package org.ratseno.demo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_member_auth")
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthNo;

    @Column(name = "user_no")
    private Long userNo;

    @Column(length = 50)
    private String auth;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updDate;

    public void setUserAuthNo(Long userAuthNo) {
        this.userAuthNo = userAuthNo;
    }

    public Long getUserAuthNo() {
        return userAuthNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    @Override
    public String toString() {
        return "MemberAuth(userAuthNo=" + getUserAuthNo() + ", userNo=" + getUserNo() + ", auth=" + getAuth() + ", regDate=" + getRegDate() + ", updDate=" + getUpdDate() + ")";
    }



    

    
    
}
