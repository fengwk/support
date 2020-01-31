package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;

/**
 * 隐藏模式api服务
 * 
 * @author fengwk
 */
public interface ImplicitModeApiService {

    /**
     * 授权认证
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO authorize(@NotNull @Valid EmailAndPasswordAuthRequestDTO requestDTO);
    
    /**
     * 授权认证
     * 
     * @param requestDTO
     * @return
     */
    TokenDTO authorize(@NotNull @Valid EmailAndRandomAuthRequestDTO requestDTO);
    
}
