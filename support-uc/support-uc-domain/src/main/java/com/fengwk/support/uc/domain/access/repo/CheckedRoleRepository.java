package com.fengwk.support.uc.domain.access.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.access.model.Role;

/**
 * 
 * @author fengwk
 */
public class CheckedRoleRepository extends CheckedRepository<Role, RoleRepository> {
    
    public CheckedRoleRepository(RoleRepository repository) {
        super(repository);
    }

    public void add(Role role) {
        checkAndThrowIfNecessary(role, () -> args(arg("role", role)));
        repository().add(role);
    }
    
    public void updateById(Role role) {
        checkAndThrowIfNecessary(role, () -> args(arg("role", role)));
        repository().updateById(role);
    }
    
    public Role getById(long id) {
        Role role = repository().getById(id);
        checkAndThrowIfNecessary(role, () -> args(arg("roleId", id)));
        return role;
    }
    
    public CheckedRoleRepository requiredNonNull() {
        appendChecker(role -> role == null ? failure("角色不存在") : success());
        return this;
    }
    
    public CheckedRoleRepository requiredNonDuplicateName() {
        appendChecker(role -> repository().existsByName(role.getName()) ? failure("已存在同名的角色") : success());
        return this;
    }
    
}
