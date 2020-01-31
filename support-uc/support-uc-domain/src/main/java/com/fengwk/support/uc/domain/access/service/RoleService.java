package com.fengwk.support.uc.domain.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class RoleService {

    @Autowired
    volatile RoleRepository roleRepository;
    
    public Role create(String name) {
        if (roleRepository.existsByName(name)) {
            log.warn("已存在同名角色,name={}.", name);
            throw ExceptionCodes.biz().create("已存在同名角色");
        }
        Role role = Role.of(name);
        roleRepository.add(role);
        return role;
    }
    
    public void remove(long id) {
        checkout(id);
        roleRepository.remove(id);
    }
    
    public void update(long id, String name) {
        Role role = checkout(id);
        role.update(name);
        roleRepository.update(role);
    }
    
    private Role checkout(long id) {
        Role role = roleRepository.get(id);
        if (role == null) {
            log.warn("角色不存在,id={}.", id);
            throw ExceptionCodes.biz().create("角色不存在");
        }
        return role;
    }
    
}
