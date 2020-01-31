package com.fengwk.support.uc.application.access.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.uc.api.access.model.PermissionAddDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.model.RoleAddDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RolePermissionApiService;
import com.fengwk.support.uc.application.access.converter.PermissionConverter;
import com.fengwk.support.uc.application.access.converter.RoleConverter;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.repo.UserRoleLinkRepository;
import com.fengwk.support.uc.domain.access.service.PermissionService;
import com.fengwk.support.uc.domain.access.service.RolePermissionService;
import com.fengwk.support.uc.domain.access.service.RoleService;
import com.fengwk.support.uc.domain.access.service.UserRoleService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class RolePermissionApiServiceImpl implements RolePermissionApiService {

    @Autowired
    volatile RoleService roleService;
    
    @Autowired
    volatile PermissionService permissionService;
    
    @Autowired
    volatile UserRoleService userRoleService;
    
    @Autowired
    volatile RolePermissionService rolePermissionService;

    @Autowired
    volatile RoleRepository roleRepository;
    
    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @Autowired
    volatile UserRoleLinkRepository userRoleLinkRepository;
    
    @Autowired
    volatile RolePermissionLinkRepository rolePermissionLinkRepository;
    
    @Override
    public RoleDTO createRole(RoleAddDTO roleAddDTO) {
        return RoleConverter.convert(roleService.create(roleAddDTO.getName()));
    }

    @Override
    public void removeRole(long roleId) {
        roleService.remove(roleId);
    }

    @Override
    public void updateRole(RoleUpdateDTO roleUpdateDTO) {
        roleService.update(roleUpdateDTO.getId(), roleUpdateDTO.getName());
    }

    @Override
    public List<RoleDTO> listRole(long userId) {
        return userRoleService.listRole(userId).stream().map(RoleConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Page<RoleDTO> pageRole(PageQueryDTO pageQueryDTO) {
        return roleRepository.page(new PageQuery(pageQueryDTO)).map(RoleConverter::convert);
    }

    @Override
    public PermissionDTO createPermission(PermissionAddDTO permissionAddDTO) {
        return PermissionConverter.convert(permissionService.create(permissionAddDTO.getName(), permissionAddDTO.getPath()));
    }

    @Override
    public void removePermission(long permissionId) {
        permissionService.remove(permissionId);
    }

    @Override
    public void updatePermission(PermissionUpdateDTO permissionUpdateDTO) {
        permissionService.update(permissionUpdateDTO.getId(), permissionUpdateDTO.getName(), permissionUpdateDTO.getPath());
    }

    @Override
    public List<PermissionDTO> listPermission(long roleId) {
        return rolePermissionService.listPermission(roleId).stream().map(PermissionConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Page<PermissionDTO> pagePermission(PageQueryDTO pageQueryDTO) {
        return permissionRepository.page(new PageQuery(pageQueryDTO)).map(PermissionConverter::convert);
    }

    @Override
    public void grantRole(long userId, long roleId) {
        userRoleService.grantRole(userId, roleId);
    }

    @Override
    public void revokeRole(long userId, long roleId) {
        userRoleService.revokeRole(userId, roleId);
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
