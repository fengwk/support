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
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class RolePermissionService {

    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @Autowired
    volatile RolePermissionLinkRepository rolePermissionLinkRepository;

    public List<Permission> listPermission(long roleId) {
        List<RolePermissionLink> rolePermissionLinks = rolePermissionLinkRepository.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(rolePermissionLinks)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissionLinks.stream().map(RolePermissionLink::getPermissionId).collect(Collectors.toList());
        return permissionRepository.list(permissionIds);
    }
    
    public void grantPermission(long roleId, long permissionId) {
        if (rolePermissionLinkRepository.exists(roleId, permissionId)) {
            return;
        }
        RolePermissionLink rolePermissionLink = RolePermissionLink.of(roleId, permissionId);
        rolePermissionLinkRepository.add(rolePermissionLink);
    }

    public void revokePermission(long roleId, long permissionId) {
        RolePermissionLink rolePermissionLink = rolePermissionLinkRepository.get(roleId, permissionId);
        if (rolePermissionLink == null) {
            return;
        }
        rolePermissionLinkRepository.remove(rolePermissionLink.getId());
    }

}
