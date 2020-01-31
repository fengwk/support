package com.fengwk.support.spring.boot.starter.concurrent.lock;

import org.redisson.api.RedissonClient;

import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.lock.DistributedLock;
import com.fengwk.support.core.lock.DistributedLockFactory;

import lombok.AllArgsConstructor;

/**
 * 
 * @author fengwk
 */
@AllArgsConstructor
public class RedissonClientAdapter implements DistributedLockFactory {

    static final String SEPARATOR = ".";
    
    final RedissonClient redissonClient;

    @Override
    public DistributedLock create(String name) {
        Preconditions.notBlank(name, "分布式锁名称不能为空");
        return new RLockAdapter(redissonClient.getLock(name));
    }

    @Override
    public DistributedLock create(Object... nameParts) {
        Preconditions.notEmpty(nameParts, "分布式锁名称不能为空");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nameParts.length; i++) {
            builder.append(nameParts[i]);
            if (i < nameParts.length - 1) {
                builder.append(SEPARATOR);
            }
        }
        return create(builder.toString());
    }
    
}
