package com.fengwk.support.uc.api.access.service;

/**
 * 
 * @author fengwk
 */
public interface UserRoleApiService {
    
    void grantRole(long userId, long roleId);
    
    void revokeRole(long userId, long roleId);
    
}
