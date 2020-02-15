package com.fengwk.support.core.convention.lock;

/**
 * 
 * @author fengwk
 */
public interface DistributedLockFactory {

    /**
     * Create DistributedLock by name
     * 
     * @param name
     * @return
     */
    DistributedLock create(String name);
    
    DistributedLock create(Object... nameParts);
    
}
