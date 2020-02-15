package com.fengwk.support.spring.boot.starter.concurrent.lock;

import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fengwk.support.core.convention.lock.DistributedLockFactory;
import com.fengwk.support.core.convention.lock.InvalidDistributedLockFactory;

/**
 * 
 * @author fengwk
 */
@Configuration
public class DistributedLockAutoConfiguration {
    
    @ConditionalOnMissingBean(ignored = InvalidDistributedLockFactory.class)
    @Bean
    public DistributedLockFactory distributedLockFactory(RedissonClient redissonClient) {
        return new RedissonClientAdapter(redissonClient);
    }
    
}