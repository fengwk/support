package com.fengwk.support.uc.api.access.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @author fengwk
 */
public interface UserPermissionApiService {

    boolean verify(long userId, @NotBlank @Valid String testPath);
    
}
