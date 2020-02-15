package com.fengwk.support.uc.api.user.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.uc.api.user.model.UserDTO;
import com.fengwk.support.uc.api.user.model.UserDescriptorDTO;
import com.fengwk.support.uc.api.user.model.UserSearchDTO;
import com.fengwk.support.uc.api.user.model.UserUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface UserApiService {
    
    boolean existsByEmail(@NotBlank String email);
    
    UserDTO updateSelective(@NotNull @Valid UserUpdateDTO updateDTO);
    
    Page<UserDTO> search(@NotNull @Valid UserSearchDTO queryDTO);
    
    UserDescriptorDTO tryGetUserDescriptor(String accessToken);
    
}
