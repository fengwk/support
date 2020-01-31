package com.fengwk.support.uc.domain.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationServerRepository;
import com.fengwk.support.uc.domain.oauth2.repo.DefaultAuthorizationServerRepository;

/**
 * 
 * @author fengwk
 */
@ComponentScan("com.fengwk.support.uc.domain")
@Configuration
public class DomainConfig {

    @ConditionalOnMissingBean
    @Bean
    public AuthorizationServerRepository authorizationServerRepository() {
        return new DefaultAuthorizationServerRepository();
    }
    
}
