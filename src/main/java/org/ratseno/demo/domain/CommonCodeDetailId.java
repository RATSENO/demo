package org.ratseno.demo.domain;

import java.io.Serializable;

public class CommonCodeDetailId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupCode;

    private String codeValue;

    public CommonCodeDetailId() {

    }

    public CommonCodeDetailId(String groupCode, String codeValue) {
        this.groupCode = groupCode;
        this.codeValue = codeValue;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
