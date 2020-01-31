package com.fengwk.support.core.query;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public final class Sorting<F extends Field> implements Condition {

    private Field field;
    
    private Order order;

    public enum Order {
        ASC,// 升序 
        DESC;// 降序
    }
    
}
