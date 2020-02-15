package com.fengwk.support.spring.boot.starter.mysql.convention;

import com.fengwk.support.core.convention.sql.SqlDetector;

/**
 * 
 * @author fengwk
 */
public class LikeDetector {

    private LikeDetector() {}
    
    public static boolean isInject(String value) {
        return value.indexOf('%') >= 0 || SqlDetector.isInject(value);
    }
    
}
