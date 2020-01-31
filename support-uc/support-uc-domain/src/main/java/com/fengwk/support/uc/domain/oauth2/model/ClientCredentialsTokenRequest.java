package com.fengwk.support.uc.domain.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 凭证模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientCredentialsTokenRequest extends BasicTokenRequest {
    
    public ClientCredentialsTokenRequest(String grantType, long clientId, String clientSecret) {
        super(grantType, clientId, clientSecret);
    }

}
