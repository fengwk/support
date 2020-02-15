package com.fengwk.support.core.convention.sql;

import com.fengwk.support.core.convention.exception.ErrorCodeException;

/**
 * 
 * @author fengwk
 */
public class SqlInjectionException extends ErrorCodeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7686568075692096479L;

    public SqlInjectionException(SqlInjectionErrorCode errorCode) {
        super(errorCode);
    }
    
}
