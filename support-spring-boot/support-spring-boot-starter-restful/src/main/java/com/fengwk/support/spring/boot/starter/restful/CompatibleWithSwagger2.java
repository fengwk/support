package com.fengwk.support.spring.boot.starter.restful;

import java.lang.reflect.Type;

import com.fengwk.support.core.gson.GsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * 
 * @author fengwk
 */
class CompatibleWithSwagger2 {
    
    static class FoxJsonSerializer implements JsonSerializer<Json> {
        
        @Override
        public JsonElement serialize(Json src, Type typeOfSrc, JsonSerializationContext context) {
            return GsonUtils.fromJson(src.value(), JsonElement.class);
        }
        
    }
    
    static class SecurityConfigurationSerializer implements JsonSerializer<SecurityConfiguration> {
        
        @Override
        public JsonElement serialize(SecurityConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
            return GsonUtils.toJsonTree(src);
        }
        
    }
    
    static class UiConfigurationSerializer implements JsonSerializer<UiConfiguration> {
        
        @Override
        public JsonElement serialize(UiConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
            return GsonUtils.toJsonTree(src);
        }
        
    }
    
    static class SwaggerResourceSerializer implements JsonSerializer<SwaggerResource> {

        @Override
        public JsonElement serialize(SwaggerResource src, Type typeOfSrc, JsonSerializationContext context) {
            return GsonUtils.toJsonTree(src);
        }
        
    }
    
}
