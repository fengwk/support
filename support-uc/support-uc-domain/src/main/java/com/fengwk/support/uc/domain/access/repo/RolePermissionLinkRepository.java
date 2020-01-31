package com.fengwk.support.uc.domain.access.repo;

import java.util.Collection;
import java.util.List;

import com.fengwk.support.uc.domain.access.model.RolePermissionLink;

/**
 * 
 * @author fengwk
 */
public interface RolePermissionLinkRepository {

    void add(RolePermissionLink rolePermissionLink);
    
    void remove(long id);
    
    void removeByPermissionId(long permissionId);
    
    RolePermissionLink get(long roleId, long permissionId);
    
    boolean exists(long roleId, long permissionId);
    
    List<RolePermissionLink> listByRoleId(long roleId);
    
    List<RolePermissionLink> listByRoleIds(Collection<Long> roleIds);
    
}
