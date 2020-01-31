package com.fengwk.support.core.query;

import lombok.Data;

@Data
public final class LessThanOrEquals<F extends Field> implements Condition {

    private Field field;
    
    private Object value;
    
}
