package com.fengwk.support.spring.boot.starter.restful;

import com.fengwk.support.core.code.AbstractErrorCode;
import com.fengwk.support.core.code.ErrorCode;

/**
 * 
 * @author fengwk
 */
public class IllegalArgumentErrorCode extends AbstractErrorCode implements ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = -5378234499279240645L;
    
    private static final String DEFAULT_CODE = "C_1000";
    
    public IllegalArgumentErrorCode(String message) {
        super(DEFAULT_CODE, message);
    }

}
