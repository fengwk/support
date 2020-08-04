package com.fengwk.support.core.util.reflect;

import java.lang.reflect.Type;

/**
 * 
 * @author fengwk
 */
public interface TypeDefinition<T> {

    default Type getDefinitionType() {
        return GenericUtils.findNearestVarType(getClass(), TypeDefinition.class, 0);
    }
    
}
