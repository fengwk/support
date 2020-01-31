package com.fengwk.support.core.query;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author fengwk
 */
@Getter
@AllArgsConstructor
public enum ConditionType {
    
    AND(1, And.class),
    OR(2, Or.class),
    SORTING(3, Sorting.class),
    EQUALS(4, Equals.class),
    IN(5, In.class),
    GREATERTHAN(6, GreaterThan.class),
    GREATERTHANOREQUALS(7, GreaterThanOrEquals.class),
    LESSTHAN(8, LessThan.class),
    LESSTHANOREQUALS(9, LessThanOrEquals.class);
    
    final int code;
    final Class<? extends Condition> clazz;
    
    public static ConditionType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ConditionType type : ConditionType.values()) {
            if (Objects.equals(type.getCode(), code)) {
                return type;
            }
        }
        return null;
    }
    
    public static ConditionType getByClazz(Class<? extends Condition> clazz) {
        if (clazz == null) {
            return null;
        }
        for (ConditionType type : ConditionType.values()) {
            if (Objects.equals(type.getClazz(), clazz)) {
                return type;
            }
        }
        return null;
    }
    
}
