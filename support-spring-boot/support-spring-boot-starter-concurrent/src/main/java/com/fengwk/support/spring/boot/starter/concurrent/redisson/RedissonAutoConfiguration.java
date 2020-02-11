package com.fengwk.support.spring.boot.starter.concurrent.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author fengwk
 */
@Configuration
public class RedissonAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public Config config(RedisProperties redisProperties) {
        Config config = new Config();
        config.setLockWatchdogTimeout(1000 * 15);
        config.useSingleServer()
        .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
        .setPassword(redisProperties.getPassword())
        .setTimeout((int) redisProperties.getTimeout().toMillis());
        return config;
    }
    
    @ConditionalOnMissingBean
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(Config config) {
        return Redisson.create(config);
    }
    
}