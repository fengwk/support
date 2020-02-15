package com.fengwk.support.uc.domain.access.model;

import org.apache.commons.lang3.StringUtils;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends UcEntity {

    String name;
    
    public static Role create(String name) {
        checkName(name);
        Role role = new Role();
        role.name = name;
        return role;
    }
    
    public void update(String name, boolean isSelective) {
        if (!isSelective || StringUtils.isNotBlank(name)) {
            checkName(name);
            this.name = name;
        }
    }
    
    private static void checkName(String name) {
        Preconditions.notBlank(name, "角色名称不能为空.");
    }
    
}
