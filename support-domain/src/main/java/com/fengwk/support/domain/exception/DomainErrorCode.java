package com.fengwk.support.domain.exception;

import com.fengwk.support.core.code.AbstractErrorCode;
import com.fengwk.support.core.code.ErrorCode;

/**
 * 
 * @author fengwk
 */
public class DomainErrorCode extends AbstractErrorCode implements ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = 7201870978938198483L;
    
    private static final String DEFAULT_CODE = "C_2000";
    
    private static final String DEFAULT_MESSAGE = "业务异常";
    
    static final DomainErrorCode NORMAL = new DomainErrorCode(DEFAULT_MESSAGE);
    
    DomainErrorCode(String customMessage) {
        super(DEFAULT_CODE, customMessage);
    }
    
}
