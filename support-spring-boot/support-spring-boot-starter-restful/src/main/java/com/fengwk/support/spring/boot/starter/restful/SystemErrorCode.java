package com.fengwk.support.spring.boot.starter.restful;

import com.fengwk.support.core.convention.code.ErrorCode;

/**
 * 
 * @author fengwk
 */
public enum SystemErrorCode implements ErrorCode {

    INSTANCE;
    
    private static final String DEFAULT_CODE = "C_9999";
    
    private static final String DEFAULT_MESSAGE = "系统异常";   

    @Override
    public String getCode() {
        return DEFAULT_CODE;
    }

    @Override
    public String getMessage() {
        return DEFAULT_MESSAGE;
    }
    
}
