package com.fengwk.support.core.util;

/**
 * 
 * @author fengwk
 */
public class StringUtils {

    private StringUtils() {}
    
    /**
     * 首字母小写
     * 
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
    
}
