package com.fengwk.support.uc.api.access.service;

import java.util.List;

import com.fengwk.support.uc.api.access.model.PermissionDTO;

/**
 * 
 * @author fengwk
 */
public interface RolePermissionApiService {
    
    List<PermissionDTO> listPermissions(long roleId);
    
    void grantPermission(long roleId, long permissionId);
    
    void revokePermission(long roleId, long permissionId);
    
}
