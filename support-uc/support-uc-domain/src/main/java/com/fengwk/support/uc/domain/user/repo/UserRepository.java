package com.fengwk.support.uc.domain.user.repo;

import com.fengwk.support.uc.domain.user.model.User;

/**
 * 
 * @author fengwk
 */
public interface UserRepository {
	
    void add(User user);
    
    void update(User user);
    
    boolean existsByEmail(String email);
    
    User getByEmail(String email);
    
}
