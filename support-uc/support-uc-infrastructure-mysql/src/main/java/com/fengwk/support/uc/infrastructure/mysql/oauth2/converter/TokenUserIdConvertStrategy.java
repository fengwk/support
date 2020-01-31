package com.fengwk.support.uc.infrastructure.mysql.oauth2.converter;

/**
 * 为避免数据库userId为null,使用0代替null值
 * 
 * @author fengwk
 */
public class TokenUserIdConvertStrategy {

    private TokenUserIdConvertStrategy() {}

    public static Long toPO(Long userId) {
        return userId == null ? 0L : userId;
    }
    
    public static Long fromPO(Long userId) {
        return userId == 0L ? null : userId;
    }
    
}
