package com.fengwk.support.uc.domain.user.repo;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.uc.domain.user.model.User;

/**
 * 
 * @author fengwk
 */
public interface UserRepository {
	
    void add(User user);
    
    void updateById(User user);
    
    boolean existsByEmail(String email);
    
    User getById(long id);
    
    User getByEmail(String email);
    
    Page<User> page(Query<User> query, PageQuery pageQuery);
    
}
