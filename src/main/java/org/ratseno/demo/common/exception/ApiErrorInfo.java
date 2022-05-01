package org.ratseno.demo.common.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiErrorInfo implements Serializable {
    private static final long serialVersionUID = -4521676718585992138L;

    private String message;

    private final List<ApiErrorDetailInfo> details = new ArrayList<>();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addDetailInfo(String target, String message) {
        this.details.add(new ApiErrorDetailInfo(target, message));
    }

    public List<ApiErrorDetailInfo> getDetails() {
        return this.details;
    }
}
