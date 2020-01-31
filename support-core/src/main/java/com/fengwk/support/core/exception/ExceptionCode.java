package com.fengwk.support.core.exception;

/**
 * 
 * @author fengwk
 */
public interface ExceptionCode<E extends Exception> extends ExceptionFactory<E> {

    /**
     * 异常码
     * 
     * @return
     */
    String code();
    
    /**
     * 异常值
     * 
     * @return
     */
    String value();
    
}
