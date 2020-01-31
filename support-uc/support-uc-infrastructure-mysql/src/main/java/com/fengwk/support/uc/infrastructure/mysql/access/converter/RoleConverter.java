package com.fengwk.support.uc.infrastructure.mysql.access.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.model.RolePO;

/**
 * 
 * @author fengwk
 */
@Component
public class RoleConverter implements UcConverter<Role, RolePO> {

    @Override
    public RolePO convert(Role entity) {
        if (entity == null) {
            return null;
        }
        RolePO rolePO = new RolePO();
        rolePO.setCreatedTime(entity.getCreatedTime());
        rolePO.setId(entity.getId());
        rolePO.setModifiedTime(entity.getModifiedTime());
        rolePO.setName(entity.getName());
        return rolePO;
    }

    @Override
    public Role convert(RolePO po) {
        if (po == null) {
            return null;
        }
        Role role = new Role();
        role.setCreatedTime(po.getCreatedTime());
        role.setId(po.getId());
        role.setModifiedTime(po.getModifiedTime());
        role.setName(po.getName());
        return role;
    }

}
