package com.fengwk.support.core.exception;

import com.fengwk.support.core.util.PlaceholderMessageFormatter;

/**
 * 
 * @author fengwk
 */
public class MessageUtils {

    private static final PlaceholderMessageFormatter FORMATTER = new PlaceholderMessageFormatter("{}");
    
    private MessageUtils() {}
    
    public static String format(String format, Object... args) {
        return FORMATTER.format(format, args);
    }
    
}
