package com.fengwk.support.core.result;

import com.fengwk.support.core.exception.ExceptionCodes;

/**
 * 
 * @author fengwk
 */
public interface BizResult extends Result {

    default RuntimeException createException() {
        return ExceptionCodes.biz().create(getMessage());
    }
    
    default void throwExceptionIfNecessary() throws RuntimeException {
        if (!isSuccess()) {
            throw createException();
        }
    }
    
}
