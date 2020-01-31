package com.fengwk.support.core.exception;

/**
 * 
 * @author fengwk
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5355154991975612630L;

    private final String code;

    public BizException(String code) {
        super();
        this.code = code;
    }
    
    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
