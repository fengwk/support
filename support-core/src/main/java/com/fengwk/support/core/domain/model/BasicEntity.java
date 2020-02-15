package com.fengwk.support.core.domain.model;

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
        this.createdTime = now;
        this.modifiedTime = now;
    }

    @Override
    public I identity() {
        return this.id;
    }
    
}
