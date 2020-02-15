package com.fengwk.support.spring.boot.starter.restful;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results.ResultImpl;
import com.fengwk.support.core.util.gson.DefaultGsonBuilderFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * 
 * @author fengwk
 */
@Configuration
public class WebAutoConfiguration {
    
    @ConditionalOnMissingBean
    @Bean
    public RestfulExceptionHandler restControllerExceptionHandler() {
        return new RestfulExceptionHandler();
    }
    
    @ConditionalOnMissingBean
    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonBuilder builder = DefaultGsonBuilderFactory.create();
        
        // 兼容swagger2
        builder.registerTypeAdapter(Json.class, new CompatibleWithSwagger2.FoxJsonSerializer());
        builder.registerTypeAdapter(SecurityConfiguration.class, new CompatibleWithSwagger2.SecurityConfigurationSerializer());
        builder.registerTypeAdapter(UiConfiguration.class, new CompatibleWithSwagger2.UiConfigurationSerializer());
        builder.registerTypeAdapter(SwaggerResource.class, new CompatibleWithSwagger2.SwaggerResourceSerializer());
        
        builder.registerTypeAdapter(Result.class, new RestfulResultTypeAdapter());
        builder.registerTypeAdapter(ResultImpl.class, new RestfulResultTypeAdapter());
        builder.setFieldNamingStrategy(FieldNamingPolicy.IDENTITY);
        return new GsonHttpMessageConverter(builder.create());
    }
    
}
