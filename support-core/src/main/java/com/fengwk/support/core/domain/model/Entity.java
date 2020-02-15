package com.fengwk.support.core.domain.model;

import java.util.Objects;

/**
 * 
 * @author fengwk
 */
public interface Entity<I> extends Identity<I> {
    
    default boolean sameAs(Entity<I> other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(identity(), other.identity());
    }

}
