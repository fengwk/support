package com.fengwk.support.uc.api.oauth2.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class TokenDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8182936226520504648L;

    /**
     * 访问令牌
     */
    String accessToken;
    
    /**
     * 令牌类型
     */
    String tokenType;
    
    /**
     * 超时时间
     */
    Integer expiresIn;
    
    /**
     * 刷新令牌
     */
    String refreshToken;
    
    /**
     * 权限范围
     */
    String scope;
    
}
