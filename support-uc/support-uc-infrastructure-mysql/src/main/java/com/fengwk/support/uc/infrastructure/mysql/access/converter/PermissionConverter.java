package com.fengwk.support.uc.infrastructure.mysql.access.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.model.PermissionPO;

/**
 * 
 * @author fengwk
 */
@Component
public class PermissionConverter implements UcConverter<Permission, PermissionPO> {

    @Override
    public PermissionPO convert(Permission entity) {
        if (entity == null) {
            return null;
        }
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setCreatedTime(entity.getCreatedTime());
        permissionPO.setId(entity.getId());
        permissionPO.setModifiedTime(entity.getModifiedTime());
        permissionPO.setName(entity.getName());
        permissionPO.setPath(entity.getPath());
        return permissionPO;
    }

    @Override
    public Permission convert(PermissionPO po) {
        if (po == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setCreatedTime(po.getCreatedTime());
        permission.setId(po.getId());
        permission.setModifiedTime(po.getModifiedTime());
        permission.setName(po.getName());
        permission.setPath(po.getPath());
        return permission;
    }

}
