package com.fengwk.support.uc.domain.oauth2.model;

import com.fengwk.support.core.domain.model.ValueObject;

import lombok.Data;

/**
 * 令牌回收请求
 * 
 * @author fengwk
 */
@Data
public class RevokeTokenRequest implements ValueObject {

    /**
     * 客户端id
     */
    final long clientId;
    
    /**
     * 客户端密钥
     */
    final String clientSecret;
    
    /**
     * 访问令牌
     */
    final String accessToken;
    
}
