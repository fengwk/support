package com.fengwk.support.spring.boot.starter.restful.auth;

import com.fengwk.support.core.convention.code.ErrorCode;
import com.fengwk.support.core.convention.exception.ErrorCodeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author fengwk
 */
public class AuthorizationException extends ErrorCodeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4872289574525467558L;

    protected AuthorizationException() {
        super(AuthorizationErrorCode.INSTANCE);
    }

    @Getter
    @AllArgsConstructor
    enum AuthorizationErrorCode implements ErrorCode {

        INSTANCE("C_9998", "认证失效");
        
        private final String code;
        
        private final String message;
        
    }
    
}
