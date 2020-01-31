package com.fengwk.support.core.query;

/**
 * 查询条件
 * 
 * @author fengwk
 */
public interface Condition {
    
    default int getCode() {
        return ConditionType.getByClazz(getClass()).getCode();
    }
    
}
