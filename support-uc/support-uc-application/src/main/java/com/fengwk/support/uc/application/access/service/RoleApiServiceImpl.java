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
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RoleApiService;
import com.fengwk.support.uc.application.access.converter.RoleConverter;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.service.RoleService;
import com.fengwk.support.uc.domain.access.service.UserRoleService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class RoleApiServiceImpl implements RoleApiService {

    @Autowired
    volatile RoleService roleService;
    
    @Autowired
    volatile UserRoleService userRoleService;
    
    @Autowired
    volatile RoleRepository roleRepository;
    
    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        return RoleConverter.convert(roleService.create(roleCreateDTO.getName()));
    }

    @Override
    public void remove(long roleId) {
        roleService.remove(roleId);
    }

    @Override
    public void update(RoleUpdateDTO roleUpdateDTO) {
        roleService.update(roleUpdateDTO.getId(), roleUpdateDTO.getName());
    }

    @Override
    public List<RoleDTO> list(long userId) {
        return userRoleService.listRole(userId).stream().map(RoleConverter::convert).collect(Collectors.toList());
    }

    @Override
    public Page<RoleDTO> page(PageQueryDTO pageQueryDTO) {
        return roleRepository.page(new PageQuery(pageQueryDTO)).map(RoleConverter::convert);
    }

}
