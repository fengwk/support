package com.fengwk.support.core.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fengwk.support.core.result.Response;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author fengwk
 */
public class DefaultGsonBuilderFactory {

    private DefaultGsonBuilderFactory() {}
    
    public static GsonBuilder create() {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();// 关闭html转义
        builder.registerTypeAdapter(Response.class, new ResponseTypeAdapter());
        builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
        builder.registerTypeAdapter(long.class, new LongTypeAdapter());
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        builder.registerTypeAdapter(java.sql.Date.class, new DateTypeAdapter());
        return builder;
    }
    
}
