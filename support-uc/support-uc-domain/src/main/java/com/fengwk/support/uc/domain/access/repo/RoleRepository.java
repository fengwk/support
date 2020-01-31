package com.fengwk.support.uc.domain.access.repo;

import java.util.Collection;
import java.util.List;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.uc.domain.access.model.Role;

/**
 * 
 * @author fengwk
 */
public interface RoleRepository {

    void add(Role role);
    
    void remove(long id);
    
    void update(Role role);

    boolean existsByName(String name);
    
    Role get(long id);
    
    List<Role> list(Collection<Long> ids);
    
    Page<Role> page(PageQuery pageQuery);
    
}
