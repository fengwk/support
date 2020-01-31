package com.fengwk.support.core.util;

/**
 * 
 * @author fengwk
 */
public class PlaceholderMessageFormatter {

    private final String placeholder;
    
    private final int placeholderLength;
    
    public PlaceholderMessageFormatter(String placeholder) {
        this.placeholder = placeholder;
        this.placeholderLength = placeholder.length();
    }
    
    public String format(String format, Object... args) {
        if (format == null || args == null || args.length == 0) {
            return format;
        }
        
        int offset = 0;
        int arrI = 0;
        int i;
        StringBuilder sb = new StringBuilder();
        while ((i = format.indexOf(placeholder, offset)) != -1 && arrI < args.length) {
            sb.append(format.substring(offset, i));
            sb.append(args[arrI++]);
            offset = i + placeholderLength;
        }
        if (offset < format.length()) {
            sb.append(format.substring(offset));
        }
        
        return sb.toString();
    }
    
}
