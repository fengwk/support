package com.fengwk.support.spring.boot.starter.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import org.redisson.api.RLock;

import com.fengwk.support.core.lock.DistributedLock;

import lombok.AllArgsConstructor;

/**
 * 
 * @author fengwk
 */
@AllArgsConstructor
public class RLockAdapter implements DistributedLock {

    final RLock rLock;
    
    @Override
    public void lock() {
        rLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        rLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return rLock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        rLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return rLock.newCondition();
    }

    @Override
    public String getName() {
        return rLock.getName();
    }

    @Override
    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
        rLock.lockInterruptibly(leaseTime, unit);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(waitTime, leaseTime, unit);
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        rLock.lock(leaseTime, unit);
    }

    @Override
    public boolean isLocked() {
        return rLock.isLocked();
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return rLock.isHeldByCurrentThread();
    }

}
