package com.fengwk.support.uc.domain.access.repo;

import java.util.Collection;
import java.util.List;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.uc.domain.access.model.Role;

/**
 * 
 * @author fengwk
 */
public interface RoleRepository {

    void add(Role role);
    
    void removeById(long id);
    
    void updateById(Role role);

    boolean existsByName(String name);
    
    Role getById(long id);
    
    List<Role> listByIds(Collection<Long> ids);
    
    Page<Role> page(Query<Role> query, PageQuery pageQuery);
    
}
