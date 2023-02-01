package com.zhaizq.framework.common.exception;

public class InternalException extends BaseRuntimeException {
    public InternalException() {
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable e) {
        super(message, e);
    }
}