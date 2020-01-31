package com.fengwk.support.uc.api.oauth2.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 令牌请求
 * 
 * @author fengwk
 */
@Data
abstract class BasicTokenRequestDTO implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3215013717502940463L;

    /**
     * 授权模式
     */
    @NotBlank
    protected String grantType;
    
    /**
     * 客户端id
     */
    @NotNull
    protected Long clientId;
    
    /**
     * 客户端密钥
     */
    @NotBlank
    protected String clientSecret;
    
}
