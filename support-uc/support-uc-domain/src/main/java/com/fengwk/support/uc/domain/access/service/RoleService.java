package com.fengwk.support.uc.domain.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.domain.exception.DomainException;
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
            log.error("存在重名角色, name={}.", name);
            throw new DomainException("存在重名角色");
        }
        Role role = Role.of(name);
        roleRepository.add(role);
        return role;
    }
    
    public void remove(long id) {
        Role role = roleRepository.get(id);
        if (role != null) {
            roleRepository.remove(id);
        }
    }
    
    public void update(long id, String name) {
        Role role = roleRepository.get(id);
        if (role == null) {
            log.error("角色不存在, roleId={}.", id);
            throw new DomainException("角色不存在");
        }
        role.update(name);
        roleRepository.update(role);
    }
    
}
