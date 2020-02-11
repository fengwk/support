package com.fengwk.support.domain.model;

/**
 * 
 * @author fengwk
 */
public interface ValueObject {

    default boolean sameAs(ValueObject other) {
        return equals(other);
    }

}
