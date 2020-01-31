package com.fengwk.support.core.reflect;

import java.lang.reflect.Type;

/**
 * 
 * @author fengwk
 */
public interface TypeDefinition<T> {

    default Type getType() {
        return GenericUtils.findNearestVarType(getClass(), TypeDefinition.class, 0);
    }
    
}
