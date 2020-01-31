package com.fengwk.support.spring.boot.starter.web;

import java.lang.reflect.Type;
import java.util.Map;

import com.fengwk.support.core.json.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import springfox.documentation.spring.web.json.Json;

/**
 * 
 * @author fengwk
 */
public class FoxJsonSerializer implements JsonSerializer<Json> {

    @Override
    public JsonElement serialize(Json src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> map = JsonUtils.fromJson(src.value(), new TypeToken<Map<String, Object>>() {}.getType());
        return context.serialize(map);
    }
    
}
