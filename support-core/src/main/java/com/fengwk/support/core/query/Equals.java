package com.fengwk.support.core.query;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public final class Equals<F extends Field> implements Condition {

    private Field field;
    
    private Object value;
    
}
