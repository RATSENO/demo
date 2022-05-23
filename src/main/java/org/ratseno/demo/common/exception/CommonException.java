package org.ratseno.demo.common.exception;

public class CommonException extends AbstractException{
    private static final long serialVersionUID = 1332393860751563853L;

    private String errorCode;

    @Override
    public String getCode() {
        return null;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public CommonException(String errorCode, String[] args) {
        super();
        this.errorCode = errorCode;
        this.args = args;
    }
}
