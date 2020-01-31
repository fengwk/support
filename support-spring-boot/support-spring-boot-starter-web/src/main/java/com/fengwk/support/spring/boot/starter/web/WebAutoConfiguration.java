package com.fengwk.support.spring.boot.starter.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.fengwk.support.core.json.DefaultGsonBuilderFactory;
import com.google.gson.GsonBuilder;

import springfox.documentation.spring.web.json.Json;

/**
 * 
 * @author fengwk
 */
@Configuration
public class WebAutoConfiguration {
    
    @ConditionalOnMissingBean
    @Bean
    public RestControllerExceptionHandler restControllerExceptionHandler() {
        return new RestControllerExceptionHandler();
    }
    
    @ConditionalOnMissingBean
    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonBuilder builder = DefaultGsonBuilderFactory.create();
        builder.registerTypeAdapter(Json.class, new FoxJsonSerializer());// 兼容swagger2
        return new GsonHttpMessageConverter(builder.create());
    }
    
}
