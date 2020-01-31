package com.fengwk.support.uc.domain.access.model;

import com.fengwk.support.core.exception.Preconditions;
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
    
    public static Role of(String name) {
        Preconditions.notBlank(name, "角色名称不能为空.");
        Role role = new Role();
        role.name = name;
        return role;
    }
    
    public void update(String name) {
        Preconditions.notBlank(name, "角色名称不能为空.");
        this.name = name;
    }
    
}
