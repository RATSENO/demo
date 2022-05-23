package org.ratseno.demo.common.domain;

public class CommonResponseHeader extends CommonVO{
    private static final long serialVersionUID = 4796517747347181798L;

    private String code;
    private String message;
    private String description;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
