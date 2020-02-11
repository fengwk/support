package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;
import java.util.Objects;

import com.fengwk.support.domain.exception.DomainException;
import com.fengwk.support.domain.model.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 
 * @author fengwk
 */
@Data
public class RedirectRule implements ValueObject {

    final Mode mode;
    final URI value;
    
    public boolean verify(URI uri) {
        switch (mode) {
        case ANY:
            return true;
        case STRICT:
            return verifyForStrict(uri);
        case HOST_MATCH:
            return verifyForHostMatch(uri);
        case PATH_PREFIX_MATCH:
            return verifyForPrefixMatch(uri);
        default:
            throw new DomainException("未知的重定向模式");
        }
    }
    
    private boolean verifyForStrict(URI uri) {
        return Objects.equals(uri.getScheme(), value.getScheme()) 
                && Objects.equals(uri.getHost(), value.getHost())
                && Objects.equals(uri.getPath(), value.getPath());
    }
    
    private boolean verifyForHostMatch(URI uri) {
        return Objects.equals(uri.getHost(), value.getHost());
    }
    
    private boolean verifyForPrefixMatch(URI uri) {
        return Objects.equals(uri.getScheme(), value.getScheme()) 
                && Objects.equals(uri.getHost(), value.getHost())
                && uri.getPath().startsWith(value.getPath());
    }
    
    @Getter
    @AllArgsConstructor
    public static enum Mode {
        
        ANY(1), STRICT(2), HOST_MATCH(3), PATH_PREFIX_MATCH(4);
        
        final int code;
        
        public static Mode getByCode(Integer code) {
            if (code == null) {
                return null;
            }
            for (Mode e : Mode.values()) {
                if (Objects.equals(e.getCode(), code)) {
                    return e;
                }
            }
            return null;
        }
        
    }
    
}
