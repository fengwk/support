package com.fengwk.support.uc.api.access.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionSearchDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface PermissionApiService {
    
    PermissionDTO create(@NotNull @Valid PermissionCreateDTO permissionCreateDTO);
    
    void remove(long permissionId);
    
    PermissionDTO updateSelective(@NotNull @Valid PermissionUpdateDTO permissionUpdateDTO);
    
    Page<PermissionDTO> search(@NotNull @Valid PermissionSearchDTO searchDTO);
    
}
