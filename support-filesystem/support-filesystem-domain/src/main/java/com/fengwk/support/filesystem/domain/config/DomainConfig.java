package com.fengwk.support.filesystem.domain.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fengwk.support.filesystem.domain.facade.LocalFilesystemSelectStrategy;
import com.fengwk.support.filesystem.domain.facade.AnyLocalFilesystemSelectStrategy;

/**
 * 
 * @author fengwk
 */
@ComponentScan("com.fengwk.support.filesystem.domain")
@Configuration
public class DomainConfig {

    @ConditionalOnMissingBean
    @Bean
    public LocalFilesystemSelectStrategy localFilesystemSelector() {
        return new AnyLocalFilesystemSelectStrategy();
    }
    
}
