package com.fengwk.support.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 
 * @author fengwk
 */
public interface Entity<I> extends Identity<I> {

    LocalDateTime getCreatedTime();

    void setCreatedTime(LocalDateTime createdTime);

    LocalDateTime getModifiedTime();

    void setModifiedTime(LocalDateTime modifiedTime);
    
    default boolean sameAs(Entity<I> other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(getId(), other.getId());
    }

}
