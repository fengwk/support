package com.fengwk.support.uc.domain.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 刷新令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshTokenRequest extends BasicTokenRequest {

    final String refreshToken;
    
    public RefreshTokenRequest(String grantType, long clientId, String clientSecret, String refreshToken) {
        super(grantType, clientId, clientSecret);
        this.refreshToken = refreshToken;
    }
    
}
