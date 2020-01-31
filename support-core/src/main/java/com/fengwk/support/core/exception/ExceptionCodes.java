package com.fengwk.support.core.exception;

/**
 * 
 * @author fengwk
 */
public class ExceptionCodes {

    static final ExceptionCode<BizException> BIZ = new AbstractExceptionCode<BizException>("1000", "业务异常") {

        @Override
        public BizException create() { return new BizException(code(), value()); }

        @Override
        public BizException create(String message) { return new BizException(code(), message); }

        @Override
        public BizException create(Throwable cause) { return new BizException(code(), cause); }

        @Override
        public BizException create(String message, Throwable cause) { return new BizException(code(), message, cause); }
        
    };
    
    static final ExceptionCode<IllegalArgumentException> ARG = new AbstractExceptionCode<IllegalArgumentException>("2000", "参数异常") {

        @Override
        public IllegalArgumentException create() { return new IllegalArgumentException(value()); }

        @Override
        public IllegalArgumentException create(String message) { return new IllegalArgumentException(message); }

        @Override
        public IllegalArgumentException create(Throwable cause) { return new IllegalArgumentException(cause); }

        @Override
        public IllegalArgumentException create(String message, Throwable cause) { return new IllegalArgumentException(message, cause); }

    };
    
    static final ExceptionCode<RuntimeException> UNKNOWN = new AbstractExceptionCode<RuntimeException>("9999", "未知异常") {

        @Override
        public RuntimeException create() { return new RuntimeException(value()); }

        @Override
        public RuntimeException create(String message) { return new RuntimeException(message); }

        @Override
        public RuntimeException create(Throwable cause) { return new RuntimeException(cause); }

        @Override
        public RuntimeException create(String message, Throwable cause) { return new RuntimeException(message, cause); }
        
    };
    
    private ExceptionCodes() {}
    
    public static ExceptionCode<IllegalArgumentException> arg() {
        return ARG;
    }
    
    public static ExceptionCode<BizException> biz() {
        return BIZ;
    }
    
    public static ExceptionCode<RuntimeException> unknown() {
        return UNKNOWN;
    }
    
}
