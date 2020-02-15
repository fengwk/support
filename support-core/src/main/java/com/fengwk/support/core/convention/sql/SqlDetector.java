package com.fengwk.support.core.convention.sql;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author fengwk
 */
public class SqlDetector {

    private static Pattern PATTERN_SQL_INJECTION = Pattern.compile(
            "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)", 
            Pattern.CASE_INSENSITIVE);
    
    private SqlDetector() {}
    
    public static boolean isInject(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return PATTERN_SQL_INJECTION.matcher(value).find();
    }
    
}
