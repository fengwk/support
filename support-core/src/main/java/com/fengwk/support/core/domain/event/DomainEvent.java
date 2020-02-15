package com.fengwk.support.core.domain.event;

/**
 * 
 * @author fengwk
 */
public interface DomainEvent {

    default boolean sameEventAs(DomainEvent other) {
        return equals(other);
    }

}