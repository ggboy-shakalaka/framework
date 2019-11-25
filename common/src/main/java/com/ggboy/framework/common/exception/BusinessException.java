package com.ggboy.framework.common.exception;

public class BusinessException extends BaseRuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}