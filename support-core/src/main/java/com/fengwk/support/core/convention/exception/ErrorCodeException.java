package com.fengwk.support.core.convention.exception;

import java.util.Objects;

import com.fengwk.support.core.convention.code.ErrorCode;

/**
 * 
 * @author fengwk
 */
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -5355154991975612630L;

    private final ErrorCode errorCode;

    protected ErrorCodeException(ErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode).getMessage());
        this.errorCode = errorCode;
    }

    protected ErrorCodeException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = Objects.requireNonNull(errorCode);
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}