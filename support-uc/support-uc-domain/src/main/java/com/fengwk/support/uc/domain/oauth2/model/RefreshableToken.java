package com.fengwk.support.uc.domain.oauth2.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshableToken extends Token {

    /**
     * 刷新令牌
     */
    protected TokenDescriptor refreshToken;
    
    public static RefreshableToken create(long clientId, Long userId, String grantType, String scope, int accessExpiresIn, int refreshExpiresIn) {
        RefreshableToken token = new RefreshableToken();
        token.clientId = clientId;
        token.userId = userId;
        token.grantType = grantType;
        token.scope = scope;
        token.tokenType = DEFAULT_TOKEN_TYPE;
        token.isInvalid = false;
        token.accessToken = TokenDescriptor.create(accessExpiresIn, token.getCreatedTime());
        token.refreshToken = TokenDescriptor.create(refreshExpiresIn, token.getCreatedTime());
        return token;
    }
    
    @Override
    public boolean isRefreshable() {
        return this.refreshToken != null;
    }
    
    @Override
    public boolean isExpired() {
        return super.isExpired() && refreshToken.isExpired();
    }
    
    /**
     * 刷新动作将会更新访问令牌
     */
    public void refresh(int accessExpiresIn) {
        this.accessToken = TokenDescriptor.create(accessExpiresIn, LocalDateTime.now());
    }
    
}
