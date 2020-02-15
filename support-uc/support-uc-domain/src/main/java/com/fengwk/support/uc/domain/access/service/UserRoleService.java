package com.fengwk.support.uc.domain.access.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.model.UserRoleLink;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.repo.UserRoleLinkRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class UserRoleService {

    @Autowired
    volatile RoleRepository roleRepository;
    
    @Autowired
    volatile UserRoleLinkRepository userRoleLinkRepository;
    
    public List<Role> listRoles(long userId) {
        List<UserRoleLink> userRoleLinks = userRoleLinkRepository.listByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleLinks)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoleLinks.stream().map(UserRoleLink::getRoleId).collect(Collectors.toList());
        return roleRepository.listByIds(roleIds);
    }
    
    public void grantRole(long userId, long roleId) {
        if (userRoleLinkRepository.exists(userId, roleId)) {
            return;
        }
        UserRoleLink userRoleLink = UserRoleLink.create(userId, roleId);
        userRoleLinkRepository.add(userRoleLink);
    }

    public void revokeRole(long userId, long roleId) {
        UserRoleLink userRoleLink = userRoleLinkRepository.get(userId, roleId);
        if (userRoleLink != null) {
            userRoleLinkRepository.removeById(userRoleLink.destroy());
        }
    }

}
