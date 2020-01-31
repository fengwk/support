package com.fengwk.support.core.json;

import java.lang.reflect.Type;
import com.google.gson.Gson;

/**
 * 
 * @author fengwk
 */
public class JsonUtils {
    
    private final static Gson GSON = DefaultGsonBuilderFactory.create().create();
    
    private JsonUtils() {}
    
    private static Gson gson() {
        return GSON;
    }
    
    public static String toJson(Object src) {
        return gson().toJson(src);
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson().fromJson(json, classOfT);
    }
    
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson().fromJson(json, typeOfT);
    }
    
}
