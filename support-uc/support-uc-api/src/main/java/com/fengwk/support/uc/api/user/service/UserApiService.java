package com.fengwk.support.uc.api.user.service;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author fengwk
 */
public interface UserApiService {
    
    boolean existsByEmail(@NotBlank String email);
    
}
