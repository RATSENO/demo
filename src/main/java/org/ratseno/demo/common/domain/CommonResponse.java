package org.ratseno.demo.common.domain;

import java.util.Map;

public class CommonResponse {
    private static final long serialVersionUID = 1638663586857148668L;

    private String code;

    private String message;

    private Map<String,Object> body;

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

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
