package com.fengwk.support.core.code;

/**
 * 
 * @author fengwk
 */
public enum SuccessCode implements Code {

    INSTANCE;
    
    private static final String DEFAULT_CODE = "C_0";
    
    private static final String DEFAULT_MESSAGE = "成功";   
    
    @Override
    public String getCode() {
        return DEFAULT_CODE;
    }
    
    @Override
    public String getMessage() {
        return DEFAULT_MESSAGE;
    }
    
}
