package com.fengwk.support.uc.domain.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class PermissionService {

    @Autowired
    volatile PermissionRepository permissionRepository;
    
    public Permission create(String name, String path) {
        if (permissionRepository.existsByName(name)) {
            log.warn("已存在同名权限,name={}.", name);
            throw ExceptionCodes.biz().create("已存在同名权限");
        }
        Permission permission = Permission.of(name, path);
        permissionRepository.add(permission);
        return permission;
    }
    
    public void remove(long id) {
        checkout(id);
        permissionRepository.remove(id);
    }
    
    public void update(long id, String name, String path) {
        Permission permission = checkout(id);
        permission.update(name, path);
        permissionRepository.update(permission);
    }
    
    private Permission checkout(long id) {
        Permission permission = permissionRepository.get(id);
        if (permission == null) {
            log.warn("权限不存在,id={}.", id);
            throw ExceptionCodes.biz().create("权限不存在");
        }
        return permission;
    }
    
}
