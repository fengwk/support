package com.fengwk.support.core.json;

import java.lang.reflect.Type;
import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses.ResponseImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

/**
 * 
 * @author fengwk
 */
public class ResponseTypeAdapter implements GsonTypeAdapter<Response<?>> {

    @Override
    public JsonElement serialize(Response<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
    
    @Override
    public Response<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return context.deserialize(json, ResponseImpl.class);
    }

}
