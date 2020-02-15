package com.fengwk.support.core.util;

import java.util.regex.Pattern;

import com.fengwk.support.core.convention.sql.SqlDetector;

/**
 * 格式校验器
 * 
 * @author fengwk
 */
public class ValidationUtils {

    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    
    private static final Pattern PATTERN_MOBILE_PHONE = Pattern.compile("^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$");

    private static final Pattern PATTERN_ID_CARD = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1−9]\\d5\\d2((0[1−9])|(10|11|12))(([0−2][1−9])|10|20|30|31)\\d2[0−9Xx])|([1−9]\\d5\\d2((0[1−9])|(10|11|12))(([0−2][1−9])|10|20|30|31)\\d2[0−9Xx])");
    
    private ValidationUtils() {}
    
    public static boolean isEmail(String email) {
        return matches(PATTERN_EMAIL, email);
    }
    
    public static boolean isMobilePhone(String mobilePhone) {
        return matches(PATTERN_MOBILE_PHONE, mobilePhone);
    }
    
    public static boolean isIdCard(String idCard) {
        return matches(PATTERN_ID_CARD, idCard);
    }
    
    private static boolean matches(Pattern pattern, String input) {
        if (input == null) {
            return false;
        }
        return pattern.matcher(input).matches();
    }
    
    public static boolean isLegalLike(String likeFragment) {
        if (likeFragment == null) {
            return true;
        }
        return likeFragment.indexOf('%') == -1 && !SqlDetector.isInject(likeFragment);
    }

}
