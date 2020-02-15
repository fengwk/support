package com.fengwk.support.uc.application.access.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.service.RolePermissionApiService;
import com.fengwk.support.uc.application.access.converter.PermissionConverter;
import com.fengwk.support.uc.domain.access.service.RolePermissionService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class RolePermissionApiServiceImpl implements RolePermissionApiService {

    @Autowired
    volatile RolePermissionService rolePermissionService;

    @Override
    public List<PermissionDTO> listPermissions(long roleId) {
        return rolePermissionService.listPermissions(roleId).stream().map(PermissionConverter::convert).collect(Collectors.toList());
    }
    
    @Override
    public void grantPermission(long roleId, long permissionId) {
        rolePermissionService.grantPermission(roleId, permissionId);
    }

    @Override
    public void revokePermission(long roleId, long permissionId) {
        rolePermissionService.revokePermission(roleId, permissionId);
    }

}
