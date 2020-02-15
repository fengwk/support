package com.fengwk.support.uc.api.access.service;

import java.util.List;

import com.fengwk.support.uc.api.access.model.RoleDTO;

/**
 * 
 * @author fengwk
 */
public interface UserRoleApiService {
    
    List<RoleDTO> listRoles(long userId);
    
    void grantRole(long userId, long roleId);
    
    void revokeRole(long userId, long roleId);
    
}
