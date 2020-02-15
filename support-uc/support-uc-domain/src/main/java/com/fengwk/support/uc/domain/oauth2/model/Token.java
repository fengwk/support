package com.fengwk.support.uc.domain.oauth2.model;

import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Token extends UcEntity {

    protected static final String DEFAULT_TOKEN_TYPE = "bearer";
    
    /**
     * 客户端id
     */
    protected long clientId;
    
    /**
     * 用户id
     */
    protected Long userId;
    
    /**
     * 令牌授予类型
     */
    protected String grantType;
    
    /**
     * 授权范围
     */
    protected String scope;
    
    /**
     * 令牌类型
     */
    protected String tokenType;
    
    /**
     * 访问令牌
     */
    protected TokenDescriptor accessToken;
    
    /**
     * 是否失效
     */
    protected boolean isInvalid;
    
    public static Token create(long clientId, Long userId, String grantType, String scope, int accessExpiresIn) {
        Token token = new Token();
        token.clientId = clientId;
        token.userId = userId;
        token.grantType = grantType;
        token.scope = scope;
        token.tokenType = DEFAULT_TOKEN_TYPE;
        token.isInvalid = false;
        token.accessToken = TokenDescriptor.create(accessExpiresIn, token.getCreatedTime());
        return token;
    }
    
    /**
     * 令牌是否可刷新
     * 
     * @return
     */
    public boolean isRefreshable() {
        return false;
    }
    
    public boolean isExpired() {
        return accessToken.isExpired();
    }
    
    public void invalid() {
        this.isInvalid = true;
    }
    
}
