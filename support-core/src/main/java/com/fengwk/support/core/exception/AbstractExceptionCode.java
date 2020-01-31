package com.fengwk.support.core.exception;

/**
 * 
 * @author fengwk
 */
abstract class AbstractExceptionCode<E extends Exception> implements ExceptionCode<E> {

    final String code;
    final String value;
    
    public AbstractExceptionCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String value() {
        return value;
    }

}
