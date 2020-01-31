package com.fengwk.support.uc.domain.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权码模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationCodeTokenRequest extends BasicTokenRequest {

    /**
     * 授权码
     */
    final String code;
    
    public AuthorizationCodeTokenRequest(String grantType, long clientId, String clientSecret, String code) {
        super(grantType, clientId, clientSecret);
        this.code = code;
    }

}
