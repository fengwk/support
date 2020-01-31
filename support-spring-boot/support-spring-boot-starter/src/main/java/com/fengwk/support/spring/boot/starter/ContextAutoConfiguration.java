package com.fengwk.support.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author fengwk
 */
@Configuration
public class ContextAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public ContextUtils contextUtils() {
        return new ContextUtils();
    }
    
}
