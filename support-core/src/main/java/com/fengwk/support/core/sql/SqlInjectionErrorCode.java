package com.fengwk.support.core.sql;

import com.fengwk.support.core.code.AbstractErrorCode;
import com.fengwk.support.core.code.ErrorCode;

/**
 * 
 * @author fengwk
 */
public class SqlInjectionErrorCode extends AbstractErrorCode implements ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = -5378234499279240645L;

    private static final String DEFAULT_CODE = "C_9000";
    
    private static final String DEFAULT_MESSAGE = "检测到sql注入";

    public SqlInjectionErrorCode(String fragment) {
        super(DEFAULT_CODE, DEFAULT_MESSAGE + wrap(fragment));
    }
    
    private static String wrap(String fragment) {
        return "[fragment=" + fragment + "]";
    }

}
