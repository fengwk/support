package com.fengwk.support.core.domain.model;

/**
 * 
 * @author fengwk
 */
public interface Destroyable<I> extends Identity<I> {

    default I destroy() {
        return identity();
    }
    
}
