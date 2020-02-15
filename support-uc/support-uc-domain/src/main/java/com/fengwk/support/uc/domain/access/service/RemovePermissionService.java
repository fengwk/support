package com.fengwk.support.uc.domain.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.repo.CheckedPermissionRepository;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class RemovePermissionService {

    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @Autowired
    volatile RolePermissionLinkRepository rolePermissionLinkRepository;
    
    public void remove(long permissionId) {
        Permission permission = new CheckedPermissionRepository(permissionRepository).requiredNonNull().getById(permissionId);
        rolePermissionLinkRepository.removeByPermissionId(permissionId);
        permissionRepository.removeById(permission.destroy());
    }
    
}
