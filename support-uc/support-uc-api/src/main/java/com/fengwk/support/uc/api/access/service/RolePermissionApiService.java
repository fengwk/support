package com.fengwk.support.uc.api.access.service;

/**
 * 
 * @author fengwk
 */
public interface RolePermissionApiService {
    
    void grantPermission(long roleId, long permissionId);
    
    void revokePermission(long roleId, long permissionId);
    
}
