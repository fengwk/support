package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.oauth2.model.ClientCredentialsTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;

/**
 * 凭证模式api服务
 * 
 * @author fengwk
 */
public interface ClientCredentialsModeApiService {

    /**
     * 授权令牌:凭证模式
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO token(@NotNull @Valid ClientCredentialsTokenRequestDTO requestDTO);
    
}
