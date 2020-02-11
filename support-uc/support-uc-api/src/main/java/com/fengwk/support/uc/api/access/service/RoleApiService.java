package com.fengwk.support.uc.api.access.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface RoleApiService {
    
    RoleDTO create(@NotNull @Valid RoleCreateDTO roleCreateDTO);
    
    void remove(long roleId);
    
    void update(@NotNull @Valid RoleUpdateDTO roleUpdateDTO);
    
    List<RoleDTO> list(long userId);

    Page<RoleDTO> page(@NotNull @Valid PageQueryDTO pageQueryDTO);
    
}
