package com.fengwk.support.core.exception;

import com.fengwk.support.core.util.PlaceholderMessageFormatter;

/**
 * 
 * @author fengwk
 */
public class ExceptionMessageUtils {

    private static final PlaceholderMessageFormatter FORMATTER = new PlaceholderMessageFormatter("{}");
    
    private ExceptionMessageUtils() {}
    
    public static String format(String format, Object... args) {
        return FORMATTER.format(format, args);
    }
    
}
