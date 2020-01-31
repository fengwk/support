package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权认证信息
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImplicitAuthRequest extends BasicAuthRequest {

    /**
     * 用户id
     */
    final long userId;

    public ImplicitAuthRequest(String responseType, long clientId, URI redirectUri, String scope, String state,
            long userId) {
        super(responseType, clientId, redirectUri, scope, state);
        this.userId = userId;
    }
    
}
