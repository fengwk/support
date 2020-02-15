package com.fengwk.support.core.util.gson;

import java.lang.reflect.Type;
import java.sql.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

/**
 * 
 * @author fengwk
 */
public class SqlDateTypeAdapter implements GsonTypeAdapter<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.getTime());
    }
    
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return json.isJsonNull() ? null : new Date(json.getAsLong());
    }
    
}
