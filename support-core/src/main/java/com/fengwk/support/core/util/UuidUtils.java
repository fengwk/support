package com.fengwk.support.core.util;

import java.util.UUID;

/**
 * 
 * @author fengwk
 */
public class UuidUtils {

    private UuidUtils() {}
    
    public static String gen() {
        return UUID.randomUUID().toString();
    }
    
    public static String genShort() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
}
