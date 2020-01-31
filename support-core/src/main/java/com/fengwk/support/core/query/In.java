package com.fengwk.support.core.query;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public final class In<F extends Field> implements Condition {

    private Field field;
    
    private List<Object> values;
    
}
