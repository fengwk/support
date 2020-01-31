package com.fengwk.support.core.lock;

/**
 * 
 * @author fengwk
 */
public class InvalidDistributedLockFactory implements DistributedLockFactory {

    @Override
    public DistributedLock create(String name) { throw new UnsupportedOperationException(); }

    @Override
    public DistributedLock create(Object... nameParts) { throw new UnsupportedOperationException(); }

}
