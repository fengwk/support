package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.oauth2.model.AuthorizationServerRedirectRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RevokeTokenRequestDTO;

/**
 * oauth2服务
 * 
 * @author fengwk
 */
public interface OAuth2ApiService {

    /**
     * 发送授权验证码
     * 
     * @param email
     * @return
     */
    int sendEmailRandom(@NotBlank String email);
    
    /**
     * 获取授权服务器的重定向地址
     * 
     * @return
     */
    String redirectUri(@NotNull @Valid AuthorizationServerRedirectRequestDTO requestDTO);
    
    /**
     * 回收令牌
     * 
     * @param requestDTO
     */
    void revoke(@NotNull @Valid RevokeTokenRequestDTO requestDTO);
    
    /**
     * 授权码模式
     * 
     * @return
     */
    AuthorizationCodeModeApiService authorizationCodeMode();
    
    /**
     * 隐藏模式
     * 
     * @return
     */
    ImplicitModeApiService implicitMode();
    
    /**
     * 密码模式
     * 
     * @return
     */
    PasswordModeApiService passwordMode();
    
    /**
     * 客户端凭证模式
     * 
     * @return
     */
    ClientCredentialsModeApiService clientCredentialsMode();
    
}
