package com.fengwk.support.uc.domain.access.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.model.RolePermissionLink;
import com.fengwk.support.uc.domain.access.model.UserRoleLink;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;
import com.fengwk.support.uc.domain.access.repo.UserRoleLinkRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class UserPermissionService {

    @Autowired
    volatile UserRoleLinkRepository userRoleLinkRepository;
    
    @Autowired
    volatile RolePermissionLinkRepository rolePermissionLinkRepository;
    
    @Autowired
    volatile PermissionRepository permissionRepository;
    
    public boolean verify(long userId, String useToVerifyPath) {
        List<Permission> permissions = listPermission(userId);
        for (Permission permission : permissions) {
            if (permission.verifyPath(useToVerifyPath)) {
                return true;
            }
        }
        return false;
    }
    
    private List<Permission> listPermission(long userId) {
        List<UserRoleLink> userRoleLinks = userRoleLinkRepository.listByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleLinks)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoleLinks.stream().map(UserRoleLink::getRoleId).collect(Collectors.toList());
        List<RolePermissionLink> rolePermissionLinks = rolePermissionLinkRepository.listByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(rolePermissionLinks)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissionLinks.stream().map(RolePermissionLink::getPermissionId).collect(Collectors.toList());
        return permissionRepository.listByIds(permissionIds);
    }

}
