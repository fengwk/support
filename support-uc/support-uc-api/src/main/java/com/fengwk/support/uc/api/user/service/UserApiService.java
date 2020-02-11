package com.fengwk.support.uc.api.user.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.uc.api.user.model.UserQueryDTO;
import com.fengwk.support.uc.api.user.model.UserEntityDTO;
import com.fengwk.support.uc.api.user.model.UserUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface UserApiService {
    
    boolean existsByEmail(@NotBlank String email);
    
    UserEntityDTO updateSelective(@NotNull @Valid UserUpdateDTO updateDTO);
    
    Page<UserEntityDTO> query(@NotNull @Valid UserQueryDTO queryDTO);
    
}
