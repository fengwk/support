package com.fengwk.support.uc.domain.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.repo.CheckedRoleRepository;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.repo.UserRoleLinkRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class RemoveRoleService {

    @Autowired
    volatile RoleRepository roleRepository;
    
    @Autowired
    volatile UserRoleLinkRepository userRoleLinkRepository;
    
    @Autowired
    volatile RolePermissionLinkRepository rolePermissionLinkRepository;
    
    public void remove(long roleId) {
        Role role = new CheckedRoleRepository(roleRepository).requiredNonNull().getById(roleId);
        userRoleLinkRepository.removeByRoleId(roleId);// TODO 在大用户量的场景下,这个可能会是一个重量级的操作
        rolePermissionLinkRepository.removeByRoleId(roleId);
        roleRepository.removeById(role.destroy());
    }
    
}
