package com.fengwk.support.core.sql;

import com.fengwk.support.core.exception.ErrorCodeException;

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
