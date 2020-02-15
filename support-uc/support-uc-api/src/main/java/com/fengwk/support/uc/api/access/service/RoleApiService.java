package com.fengwk.support.uc.api.access.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleSearchDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface RoleApiService {
    
    RoleDTO create(@NotNull @Valid RoleCreateDTO roleCreateDTO);
    
    void remove(long roleId);
    
    RoleDTO updateSelective(@NotNull @Valid RoleUpdateDTO roleUpdateDTO);
    
    Page<RoleDTO> search(@NotNull @Valid RoleSearchDTO searchDTO);
    
}
