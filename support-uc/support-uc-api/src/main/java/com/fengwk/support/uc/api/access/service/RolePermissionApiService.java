package com.fengwk.support.uc.api.access.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.uc.api.access.model.PermissionAddDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.model.RoleAddDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface RolePermissionApiService {
    
    /* role */

    RoleDTO createRole(@NotNull @Valid RoleAddDTO roleAddDTO);
    
    void removeRole(long roleId);
    
    void updateRole(@NotNull @Valid RoleUpdateDTO roleUpdateDTO);
    
    List<RoleDTO> listRole(long userId);

    Page<RoleDTO> pageRole(@NotNull @Valid PageQueryDTO pageQueryDTO);
    
    /* permission */
    
    PermissionDTO createPermission(@NotNull @Valid PermissionAddDTO permissionAddDTO);
    
    void removePermission(long permissionId);
    
    void updatePermission(@NotNull @Valid PermissionUpdateDTO permissionUpdateDTO);
    
    List<PermissionDTO> listPermission(long roleId);
    
    Page<PermissionDTO> pagePermission(@NotNull @Valid PageQueryDTO pageQueryDTO);
    
    /* operations */
    
    void grantRole(long userId, long roleId);
    
    void revokeRole(long userId, long roleId);
    
    void grantPermission(long roleId, long permissionId);
    
    void revokePermission(long roleId, long permissionId);
    
}
