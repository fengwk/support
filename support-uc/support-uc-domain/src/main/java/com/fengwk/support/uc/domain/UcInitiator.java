package com.fengwk.support.uc.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.lock.DistributedLock;
import com.fengwk.support.core.lock.DistributedLockFactory;
import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.domain.access.service.RolePermissionService;
import com.fengwk.support.uc.domain.access.service.UserRoleService;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RedirectRule;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

/**
 * 
 * @author fengwk
 */
@Component
public class UcInitiator {

    static final String LOCK = UcInitiator.class + ".LOCK";
    
    @Value("${uc.oauth2.clientId}")
    volatile Long ucClientId;
    
    @Value("${uc.oauth2.clientSecret}")
    volatile String ucClientSecret;
    
    @Autowired
    volatile DistributedLockFactory distributedLockFactory;
    
    @Autowired
    volatile UserRoleService userRoleService;
    
    @Autowired
    volatile RolePermissionService rolePermissionService;
    
    @Autowired
    volatile ClientRepository clientRepository;
    
    @Autowired
    volatile UserRepository userRepository;
    
    @Autowired
    volatile RoleRepository roleRepository;
    
    @Autowired
    volatile PermissionRepository permissionRepository;
    
    @PostConstruct
    public void init() {
        DistributedLock lock = distributedLockFactory.create(LOCK);
        if (!lock.tryLock()) {
            return;
        }
        try {
            initUcClient();
            initUcAdmin();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
    
    static final String UC_CLIENT_NAME = "uc";
    static final int UC_ACCESS_EXPIRES_IN = 60 * 60 * 2;
    static final int UC_REFRESH_EXPIRES_IN = 60 * 60 * 24 * 30;
    
    private void initUcClient() {
        if (clientRepository.getById(ucClientId) != null) {
            return;
        }
        
        List<RedirectRule> redirectRules = new ArrayList<>();
        redirectRules.add(new RedirectRule(RedirectRule.Mode.ANY, null));
        Client client = Client.of(UC_CLIENT_NAME, redirectRules, UC_ACCESS_EXPIRES_IN, UC_REFRESH_EXPIRES_IN, true, 1);
        clientRepository.add(client);
    }
    
    static final String UC_ADMIN_EMAIL = "admin@fengwk.com";
    static final String UC_ADMIN_PASSWORD = "admin";
    static final String UC_ADMIN_ROLE = "/uc/admin";
    static final String UC_ADMIN_PERMISSION = "/uc";
    
    private void initUcAdmin() {
        if (userRepository.getByEmail(UC_ADMIN_EMAIL) != null) {
            return;
        }
        
        User user = User.of(UC_ADMIN_EMAIL, UC_ADMIN_EMAIL, UC_ADMIN_PASSWORD);
        userRepository.add(user);
        
        Role role = Role.of(UC_ADMIN_ROLE);
        roleRepository.add(role);
        
        Permission permission = Permission.of(UC_ADMIN_PERMISSION, UC_ADMIN_PERMISSION);
        permissionRepository.add(permission);
        
        userRoleService.grantRole(user.getId(), role.getId());
        rolePermissionService.grantPermission(role.getId(), permission.getId());
    }
    
}
