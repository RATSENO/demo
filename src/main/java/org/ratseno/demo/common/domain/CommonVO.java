package org.ratseno.demo.common.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CommonVO implements Serializable{

    @JsonIgnore
    private Long regUserNo;

    @JsonIgnore
    private Long updUserNo;
    
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime regDate;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updDate;

    public Long getRegUserNo() {
        return regUserNo;
    }

    public void setRegUserNo(Long regUserNo) {
        this.regUserNo = regUserNo;
    }

    public Long getUpdUserNo() {
        return updUserNo;
    }
    
    public void setUpdUserNo(Long updUserNo) {
        this.updUserNo = updUserNo;
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
}
