package com.fengwk.support.uc.domain.oauth2.model;

import com.fengwk.support.core.domain.model.ValueObject;
import com.fengwk.support.uc.domain.oauth2.OAuth2Constants;

import lombok.Data;

/**
 * 令牌请求
 * 
 * @author fengwk
 */
@Data
class BasicTokenRequest implements ValueObject {
    
    /**
     * 授权模式
     * 
     * @see OAuth2Constants#GRANT_TYPE_AUTHORIZATION_CODE
     * @see OAuth2Constants#GRANT_TYPE_PASSWORD
     * @see OAuth2Constants#GRANT_TYPE_CLIENT_CREDENTIALS
     * @see OAuth2Constants#GRANT_TYPE_REFRESH_TOKEN
     */
    protected final String grantType;
    
    /**
     * 客户端id
     */
    protected final long clientId;
    
    /**
     * 客户端密钥
     */
    protected final String clientSecret;
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    /**
     * 测试请求是否为授权码模式
     * https://b.com/oauth/token?client_id=CLIENT_ID&client_secret=CLIENT_SECRET&grant_type=authorization_code&code=AUTHORIZATION_CODE&redirect_uri=CALLBACK_URL
     * 
     * @return
     */
    public boolean isAuthorizationCodeMode() {
        return OAuth2Constants.GRANT_TYPE_AUTHORIZATION_CODE.equals(grantType);
    }
    
    /**
     * 测试请求是否为密码模式
     * https://oauth.b.com/token?grant_type=password&username=USERNAME&password=PASSWORD&client_id=CLIENT_ID
     * 
     * @return
     */
    public boolean isPasswordMode() {
        return OAuth2Constants.GRANT_TYPE_PASSWORD.equals(grantType);
    }
    
    /**
     * 测试请求是否为凭证模式
     * https://oauth.b.com/token?grant_type=client_credentials&client_id=CLIENT_ID&client_secret=CLIENT_SECRET
     * 
     * @return
     */
    public boolean isClientCredentialsMode() {
        return OAuth2Constants.GRANT_TYPE_CLIENT_CREDENTIALS.equals(grantType);
    }
    
    /**
     * 测试请求是否为刷新令牌请求
     * https://b.com/oauth/token?grant_type=refresh_token&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&refresh_token=REFRESH_TOKEN
     * 
     * @return
     */
    public boolean isRefreshToken() {
        return OAuth2Constants.GRANT_TYPE_REFRESH_TOKEN.equals(grantType);
    }
    
}
