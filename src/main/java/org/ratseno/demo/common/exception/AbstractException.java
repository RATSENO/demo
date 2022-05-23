package org.ratseno.demo.common.exception;

public abstract class AbstractException extends RuntimeException{
    private static final long serialVersionUID = -1663527949838322012L;

    protected Object[] args;

    public abstract String getCode();

    public AbstractException() {
        super();
    }

    public AbstractException(final Object... args) {
        super();
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }        
}
