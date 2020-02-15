package com.fengwk.support.core.convention.query;

import com.fengwk.support.core.util.bean.Property;

import lombok.Data;

/**
 * 条件
 * 
 * @author fengwk
 */
@Data
public class Criterion<T> {

    final Property<T, ?> property;
    final Object value;
    final Object secondValue;
    final Condition condition;
    final AndOr andOr;
    
    Criterion(Property<T, ?> property, Condition condition, AndOr andOr) {
        this(property, null, null, condition, andOr);
    }
    
    Criterion(Property<T, ?> property, Object value, Condition condition, AndOr andOr) {
        this(property, value, null, condition, andOr);
    }
    
    Criterion(Property<T, ?> property, Object value, Object secondValue, Condition condition, AndOr andOr) {
        this.property = property;
        this.value = value;
        this.secondValue = secondValue;
        this.condition = condition;
        this.andOr = andOr;
    }

    public static enum Condition {
        
        IS_NULL,// is null
        IS_NOT_NULL,// is not null
        EQUAL_TO,// =
        NOT_EQUAL_TO,// <>
        GREATER_THAN,// >
        GREATER_THAN_OR_EQUAL_TO,// >=
        LESS_THAN,// <
        LESS_THAN_OR_EQUAL_TO,// <=
        IN,// in
        NOT_IN,// not in
        BETWEEN,// between
        NOT_BETWEEN,// not between
        LIKE,// like
        NOT_LIKE,// not like
        LIKE_PREFIX,// like
        NOT_LIKE_PREFIX;// not like

    }
    
}
