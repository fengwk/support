package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.oauth2.model.AuthorizationCodeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RefreshTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;

/**
 * 授权码模式api服务
 * 
 * @author fengwk
 */
public interface AuthorizationCodeModeApiService {

    /**
     * 授权认证
     * 
     * @param requestDTO
     * @return
     */
    String authorize(@NotNull @Valid EmailAndPasswordAuthRequestDTO requestDTO);
    
    /**
     * 授权认证
     * 
     * @param requestDTO
     * @return
     */
    String authorize(@NotNull @Valid EmailAndRandomAuthRequestDTO requestDTO);
    
    /**
     * 授权令牌:授权码模式
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO token(@NotNull @Valid AuthorizationCodeTokenRequestDTO requestDTO);
    
    /**
     * 授权令牌:刷新令牌
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO token(@NotNull @Valid RefreshTokenRequestDTO requestDTO);
    
}
