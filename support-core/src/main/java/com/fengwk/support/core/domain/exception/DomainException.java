package com.fengwk.support.core.domain.exception;

import com.fengwk.support.core.convention.code.ErrorCode;
import com.fengwk.support.core.convention.exception.ErrorCodeException;

/**
 * 
 * @author fengwk
 */
public class DomainException extends ErrorCodeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4474243869694650816L;

    public DomainException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DomainException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
    
    public DomainException(String message) {
        this(new DomainErrorCode(message));
    }
    
    public DomainException(Throwable cause) {
        this(DomainErrorCode.NORMAL, cause);
    }
    
    public DomainException() {
        this(DomainErrorCode.NORMAL);
    }
    
}
