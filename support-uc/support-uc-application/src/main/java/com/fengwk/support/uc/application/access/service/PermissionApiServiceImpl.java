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
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.service.PermissionApiService;
import com.fengwk.support.uc.application.access.converter.PermissionConverter;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.service.PermissionService;
import com.fengwk.support.uc.domain.access.service.RolePermissionService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class PermissionApiServiceImpl implements PermissionApiService {

    @Autowired
    volatile PermissionService permissionService;
    
    @Autowired
    volatile RolePermissionService rolePermissionService;
    
    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @Override
    public PermissionDTO create(PermissionCreateDTO permissionCreateDTO) {
        return PermissionConverter.convert(permissionService.create(permissionCreateDTO.getName(), permissionCreateDTO.getPath()));
    }

    @Override
    public void remove(long permissionId) {
        permissionService.remove(permissionId);
    }

    @Override
    public void update(PermissionUpdateDTO permissionUpdateDTO) {
        permissionService.update(permissionUpdateDTO.getId(), permissionUpdateDTO.getName(), permissionUpdateDTO.getPath());
    }

    @Override
    public List<PermissionDTO> list(long roleId) {
        return rolePermissionService.listPermission(roleId).stream().map(PermissionConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Page<PermissionDTO> page(PageQueryDTO pageQueryDTO) {
        return permissionRepository.page(new PageQuery(pageQueryDTO)).map(PermissionConverter::convert);
    }

}
