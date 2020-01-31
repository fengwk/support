package com.fengwk.support.uc.infrastructure.mysql.access.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.access.model.RolePermissionLink;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.model.RolePermissionLinkPO;

/**
 * 
 * @author fengwk
 */
@Component
public class RolePermissionLinkConverter implements UcConverter<RolePermissionLink, RolePermissionLinkPO> {

    @Override
    public RolePermissionLinkPO convert(RolePermissionLink entity) {
        if (entity == null) {
            return null;
        }
        RolePermissionLinkPO rolePermissionLinkPO = new RolePermissionLinkPO();
        rolePermissionLinkPO.setCreatedTime(entity.getCreatedTime());
        rolePermissionLinkPO.setId(entity.getId());
        rolePermissionLinkPO.setModifiedTime(entity.getModifiedTime());
        rolePermissionLinkPO.setPermissionId(entity.getPermissionId());
        rolePermissionLinkPO.setRoleId(entity.getRoleId());
        return rolePermissionLinkPO;
    }

    @Override
    public RolePermissionLink convert(RolePermissionLinkPO po) {
        if (po == null) {
            return null;
        }
        RolePermissionLink rolePermissionLink = new RolePermissionLink();
        rolePermissionLink.setCreatedTime(po.getCreatedTime());
        rolePermissionLink.setId(po.getId());
        rolePermissionLink.setModifiedTime(po.getModifiedTime());
        rolePermissionLink.setPermissionId(po.getPermissionId());
        rolePermissionLink.setRoleId(po.getRoleId());
        return rolePermissionLink;
    }

}
