package com.fengwk.support.spring.boot.starter.restful.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author fengwk
 * @since 2020-02-15 20:42
 */
@Configuration
public class AuthorizationAutoConfiguration implements WebMvcConfigurer {

    @ConditionalOnMissingBean
    @Bean
    public Authenticator authenticator() {
        return new DummyAuthenticator();
    }
    
    @ConditionalOnMissingBean
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
