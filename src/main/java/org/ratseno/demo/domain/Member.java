package org.ratseno.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String userId;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String userPw;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String userName;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    private List<MemberAuth> authList = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updDate;

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<MemberAuth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<MemberAuth> authList) {
        this.authList = authList;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Member)) {
            return false;
        }

        Member other = (Member) obj;
        if (!other.canEqual(this)) {
            return false;
        }

        Object this$userNo = getUserNo();
        Object other$userNo = other.getUserNo();
        return !((this$userNo == null) ? (other$userNo != null) : !this$userNo.equals(other$userNo));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Member;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $userNo = getUserNo();
        return result * 59 + (($userNo == null) ? 43 : $userNo.hashCode());
    }

    public String toString() {
        return "Member(userNo=" + getUserNo() + ", userId=" + getUserId() + ", userPw=" + getUserPw() + ", userName="
                + getUserName() + ", regDate=" + getRegDate()
                + ", updDate=" + getUpdDate() + ", authList=" + getAuthList() + ")";
    }
}
