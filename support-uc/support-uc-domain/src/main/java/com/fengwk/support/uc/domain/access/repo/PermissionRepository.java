package com.fengwk.support.uc.domain.access.repo;

import java.util.Collection;
import java.util.List;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.uc.domain.access.model.Permission;

/**
 * 
 * @author fengwk
 */
public interface PermissionRepository {

    void add(Permission permission);
    
    void removeById(long id);
    
    void updateById(Permission permission);
    
    boolean existsByName(String name);
    
    Permission getById(long id);
    
    List<Permission> listByIds(Collection<Long> ids);
    
    Page<Permission> page(Query<Permission> query, PageQuery pageQuery);
    
}
