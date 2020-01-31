package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权服务器重定向请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationServerRedirectRequest extends BasicAuthRequest {

    public AuthorizationServerRedirectRequest(String responseType, long clientId, URI redirectUri, String scope, String state) {
        super(responseType, clientId, redirectUri, scope, state);
    }
    
}
