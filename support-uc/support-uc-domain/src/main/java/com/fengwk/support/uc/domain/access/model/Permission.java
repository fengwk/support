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
public class Permission extends UcEntity {

    String name;
    String path;
    
    public static Permission create(String name, String path) {
        checkName(name);
        checkPath(path);
        Permission permission = new Permission();
        permission.name = name;
        permission.path = path;
        return permission;
    }
    
    public void update(String name, String path, boolean isSelective) {
        if (!isSelective || StringUtils.isNotBlank(name)) {
            checkName(name);
            this.name = name;
        }
        if (!isSelective || StringUtils.isNotBlank(path)) {
            checkPath(path);
            this.path = path;
        }
    }
    
    public boolean verifyPath(String useToVerifyPath) {
        if (StringUtils.isBlank(useToVerifyPath)) {
            return false;
        }
        return useToVerifyPath.startsWith(this.path);
    }
    
    private static void checkName(String name) {
        Preconditions.notBlank(name, "权限名称不能为空.");
    }
    
    private static void checkPath(String path) {
        Preconditions.notBlank(path, "权限路径不能为空.");
    }
    
}
