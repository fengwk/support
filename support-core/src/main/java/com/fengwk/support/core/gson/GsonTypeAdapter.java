package com.fengwk.support.core.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface GsonTypeAdapter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
