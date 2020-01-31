package com.fengwk.support.core.exception;

/**
 * 
 * @author fengwk
 */
public interface ExceptionFactory<E extends Exception> {

    E create();
    
    E create(String message);
    
    E create(Throwable cause);
    
    E create(String message, Throwable cause);
    
}
