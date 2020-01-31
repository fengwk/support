package com.fengwk.support.uc.domain.oauth2;

/**
 * 
 * @author fengwk
 */
public class Constants {

    // http://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html

    /**
     * 授权码模式
     */
    public static final String RESPONSE_TYPE_CODE = "code";
    
    /**
     * 隐藏模式
     */
    public static final String RESPONSE_TYPE_TOKEN = "token";
    
    /**
     * 授权码模式
     */
    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    
    /**
     * 隐藏模式
     */
    public static final String GRANT_TYPE_IMPLICIT = "implicit";
    
    /**
     * 密码模式
     */
    public static final String GRANT_TYPE_PASSWORD = "password";
    
    /**
     * 凭证模式
     */
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    
    /**
     * 刷新令牌
     */
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    
}
