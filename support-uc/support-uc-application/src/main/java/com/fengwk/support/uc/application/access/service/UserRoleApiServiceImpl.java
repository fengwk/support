package com.fengwk.support.uc.application.access.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.service.UserRoleApiService;
import com.fengwk.support.uc.application.access.converter.RoleConverter;
import com.fengwk.support.uc.domain.access.service.UserRoleService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class UserRoleApiServiceImpl implements UserRoleApiService {

    @Autowired
    volatile UserRoleService userRoleService;
    
    @Override
    public List<RoleDTO> listRoles(long userId) {
        return userRoleService.listRoles(userId).stream().map(RoleConverter::convert).collect(Collectors.toList());
    }
    
    @Override
    public void grantRole(long userId, long roleId) {
        userRoleService.grantRole(userId, roleId);
    }

    @Override
    public void revokeRole(long userId, long roleId) {
        userRoleService.revokeRole(userId, roleId);
    }

}
