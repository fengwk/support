package com.fengwk.support.uc.api.access.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface PermissionApiService {
    
    PermissionDTO create(@NotNull @Valid PermissionCreateDTO permissionCreateDTO);
    
    void remove(long permissionId);
    
    void update(@NotNull @Valid PermissionUpdateDTO permissionUpdateDTO);
    
    List<PermissionDTO> list(long roleId);
    
    Page<PermissionDTO> page(@NotNull @Valid PageQueryDTO pageQueryDTO);
    
}
