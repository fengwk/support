package com.fengwk.support.core.gson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.fengwk.support.core.result.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * 
 * @author fengwk
 */
public class GsonUtils {

    private final static Gson GSON;

    static {
        GsonBuilder builder = DefaultGsonBuilderFactory.create();
        builder.registerTypeAdapter(Result.class, new ResultTypeAdapter());
        GSON = builder.create();
    }

    private GsonUtils() {
    }

    private static Gson gson() {
        return GSON;
    }

    public static JsonElement toJsonTree(Object src) {
        return gson().toJsonTree(src);
    }

    public static JsonElement toJsonTree(Object src, Type typeOfSrc) {
        return gson().toJsonTree(src, typeOfSrc);
    }

    public static String toJson(Object src) {
        return gson().toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return gson().toJson(src, typeOfSrc);
    }

    public static void toJson(Object src, Appendable writer) throws JsonIOException {
        gson().toJson(src, writer);
    }

    public static void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        gson().toJson(src, typeOfSrc, writer);
    }

    public static void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        gson().toJson(src, typeOfSrc, writer);
    }

    public static String toJson(JsonElement jsonElement) {
        return gson().toJson(jsonElement);
    }

    public static void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        gson().toJson(jsonElement, writer);
    }

    public static JsonWriter newJsonWriter(Writer writer) throws IOException {
        return gson().newJsonWriter(writer);
    }

    public static JsonReader newJsonReader(Reader reader) {
        return gson().newJsonReader(reader);
    }

    public static void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        gson().toJson(jsonElement, writer);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return gson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return gson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return gson().fromJson(json, typeOfT);
    }

}
