package com.fengwk.support.core.convention.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 
 * @author fengwk
 */
public interface DistributedLock extends Lock {
    
    String getName();

    void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    void lock(long leaseTime, TimeUnit unit);

    /**
     * Checks if this lock locked by any thread
     * 
     * @return
     */
    boolean isLocked();

    /**
     * Checks if this lock is held by the current thread
     * 
     * @return
     */
    boolean isHeldByCurrentThread();

}
