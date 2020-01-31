package com.fengwk.support.uc.domain.access.repo;

import java.util.Collection;
import java.util.List;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.uc.domain.access.model.Permission;

/**
 * 
 * @author fengwk
 */
public interface PermissionRepository {

    void add(Permission permission);
    
    void remove(long id);
    
    void update(Permission permission);
    
    boolean existsByName(String name);
    
    Permission get(long id);
    
    List<Permission> list(Collection<Long> ids);
    
    Page<Permission> page(PageQuery pageQuery);
    
}
