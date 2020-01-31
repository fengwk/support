package com.fengwk.support.uc.domain.access.model;

import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePermissionLink extends UcEntity {
    
    long roleId;
    long permissionId;
    
    public static RolePermissionLink of(long roleId, long permissionId) {
        RolePermissionLink rolePermissionLink = new RolePermissionLink();
        rolePermissionLink.roleId = roleId;
        rolePermissionLink.permissionId = permissionId;
        return rolePermissionLink;
    }
    
}
