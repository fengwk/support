package com.fengwk.support.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public abstract class BasicEntity<I> implements Entity<I> {

    protected I id;
    protected LocalDateTime createdTime;
    protected LocalDateTime modifiedTime;
    
    protected BasicEntity(I id) {
        this.id = id;
        LocalDateTime now = LocalDateTime.now();
        createdTime = now;
        modifiedTime = now;
    }
    
}
