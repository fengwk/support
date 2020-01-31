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
public class Permission extends UcEntity {

    String name;
    String path;
    
    public static Permission of(String name, String path) {
        Preconditions.notBlank(name, "权限名称不能为空.");
        Preconditions.notBlank(path, "权限路径不能为空.");
        Permission permission = new Permission();
        permission.name = name;
        permission.path = path;
        return permission;
    }
    
    public void update(String name, String path) {
        Preconditions.notBlank(name, "权限名称不能为空.");
        Preconditions.notBlank(path, "权限路径不能为空.");
        this.name = name;
        this.path = path;
    }
    
    public boolean verify(String appliedPath) {
        return appliedPath.startsWith(path);
    }
    
}
