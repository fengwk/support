package com.fengwk.support.uc.application.access.converter;

import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.domain.access.model.Permission;

/**
 * 
 * @author fengwk
 */
public class PermissionConverter {

    private PermissionConverter() {}
    
    public static PermissionDTO convert(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setName(permission.getName());
        permissionDTO.setPath(permission.getPath());
        permissionDTO.setCreatedTime(permission.getCreatedTime());
        permissionDTO.setModifiedTime(permission.getModifiedTime());
        return permissionDTO;
    }
    
}
