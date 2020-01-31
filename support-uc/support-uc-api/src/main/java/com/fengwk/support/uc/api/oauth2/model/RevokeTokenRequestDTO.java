package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 令牌回收请求
 * 
 * @author fengwk
 */
@Data
public class RevokeTokenRequestDTO {

    /**
     * 客户端id
     */
    @NotNull
    Long clientId;
    
    /**
     * 客户端密钥
     */
    @NotBlank
    String clientSecret;
    
    /**
     * 访问令牌
     */
    @NotBlank
    String accessToken;
    
}
