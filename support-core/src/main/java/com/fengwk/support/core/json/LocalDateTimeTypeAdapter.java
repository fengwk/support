package com.fengwk.support.core.json;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
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
public class LocalDateTimeTypeAdapter implements GsonTypeAdapter<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonNull()) {
            return null;
        }
        String formatted = json.getAsString();
        return LocalDateTime.parse(formatted);
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }

}
