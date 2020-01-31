package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;

/**
 * 密码码模式api服务
 * 
 * @author fengwk
 */
public interface PasswordModeApiService {
    
    /**
     * 授权令牌
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO token(@NotNull @Valid EmailAndPasswordTokenRequestDTO requestDTO);
    
}
