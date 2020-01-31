package com.fengwk.support.core.idgen;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author fengwk
 */
public class MemoryLongIdGenerator implements IdGenerator<Long> {

    final AtomicLong contour = new AtomicLong(1);
    
    @Override
    public Long next() {
        return contour.getAndIncrement();
    }

}
