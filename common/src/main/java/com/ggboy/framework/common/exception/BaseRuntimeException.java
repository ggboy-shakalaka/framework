package com.ggboy.framework.common.exception;

public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}