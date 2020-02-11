package com.fengwk.support.core.gson;

import java.lang.reflect.Type;

import com.fengwk.support.core.gson.GsonTypeAdapter;
import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results.ResultImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
public class ResultTypeAdapter implements GsonTypeAdapter<Result<?>> {

    @Override
    public JsonElement serialize(Result<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
    
    @Override
    public Result<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, ResultImpl.class);
    }
    
    @Data
    static class RestfulResult {
        
        String error = "INTERNAL SERVER ERROR";
        String code;
        
        public RestfulResult(String error, String code) {
            this.error = error;
            this.code = code;
        }
        
    }

}
