package org.ratseno.demo.common.exception;

public class ApiErrorDetailInfo {
    private String target;

    private String message;

    public ApiErrorDetailInfo(String target, String message) {
        this.target = target;
        this.message = message;
    }

    public String getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }
}
