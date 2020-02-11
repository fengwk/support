package com.fengwk.support.core.query;

import com.fengwk.support.core.bean.Property;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class OrderBy<T> {

    final Property<T, ?> property;
    final Direction direction;
    
    OrderBy(Property<T, ?> property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }
    
    public static enum Direction {
        
        ASC, DESC;
        
    }
    
}
