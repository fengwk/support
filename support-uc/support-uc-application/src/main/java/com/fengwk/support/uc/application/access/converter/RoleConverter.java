package com.fengwk.support.uc.application.access.converter;

import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.domain.access.model.Role;

/**
 * 
 * @author fengwk
 */
public class RoleConverter {

    private RoleConverter() {}
    
    public static RoleDTO convert(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
    
}
