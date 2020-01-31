package com.fengwk.support.uc.domain.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PasswordTokenRequest extends BasicTokenRequest {

    /**
     * 用户id
     */
    final long userId;
    
    public PasswordTokenRequest(String grantType, long clientId, String clientSecret, long userId) {
        super(grantType, clientId, clientSecret);
        this.userId = userId;
    }

}
