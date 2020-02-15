package com.fengwk.support.spring.boot.starter.restful;

import java.lang.reflect.Type;

import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results.ResultImpl;
import com.fengwk.support.core.util.gson.GsonTypeAdapter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
public class RestfulResultTypeAdapter implements GsonTypeAdapter<Result<?>> {
    
    @Override
    public JsonElement serialize(Result<?> src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isSuccess()) {
            return context.serialize(src.getData());
        } else {
            return context.serialize(new RestfulResult(src.getMessage(), src.getCode()));
        }
    }
    
    @Override
    public Result<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, ResultImpl.class);
    }
    
    @Data
    static class RestfulResult {
        
        String error;
        String code;
        
        public RestfulResult(String error, String code) {
            this.error = error;
            this.code = code;
        }
        
    }

}
