package com.fengwk.support.uc.domain.access.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.access.model.Permission;

/**
 * 
 * @author fengwk
 */
public class CheckedPermissionRepository extends CheckedRepository<Permission, PermissionRepository> {
    
    public CheckedPermissionRepository(PermissionRepository repository) {
        super(repository);
    }

    public void add(Permission permission) {
        checkAndThrowIfNecessary(permission, () -> args(arg("permission", permission)));
        repository().add(permission);
    }
    
    public void updateById(Permission permission) {
        checkAndThrowIfNecessary(permission, () -> args(arg("permission", permission)));
        repository().updateById(permission);
    }
    
    public Permission getById(long id) {
        Permission permission = repository().getById(id);
        checkAndThrowIfNecessary(permission, () -> args(arg("permissionId", id)));
        return permission;
    }
    
    public CheckedPermissionRepository requiredNonNull() {
        appendChecker(role -> role == null ? failure("权限不存在") : success());
        return this;
    }
    
    public CheckedPermissionRepository requiredNonDuplicateName() {
        appendChecker(role -> repository().existsByName(role.getName()) ? failure("已存在同名的权限") : success());
        return this;
    }
    
}
