package com.fengwk.support.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author fengwk
 */
public final class Constants {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    
    public static final String DEFAULT_CHARSET_NAME = DEFAULT_CHARSET.name();
    
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    
    private Constants() {}
    
}
