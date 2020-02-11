package com.fengwk.support.core.code;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author fengwk
 */
public abstract class AbstractErrorCode implements ErrorCode {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    protected final String code;
    protected final String message;
    
    protected AbstractErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.code)
                .append(this.message)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        AbstractErrorCode other = (AbstractErrorCode) obj;
        return new EqualsBuilder()
                .append(this.code, other.code)
                .append(this.message, other.message)
                .build();
    }

    @Override
    public String toString() {
        return getClass() + " [code=" + code + ", message=" + message + "]";
    }

}
