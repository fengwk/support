package com.fengwk.support.uc.domain.access.repo;

import java.util.List;

import com.fengwk.support.uc.domain.access.model.UserRoleLink;

/**
 * 
 * @author fengwk
 */
public interface UserRoleLinkRepository {

    void add(UserRoleLink userRoleLink);
    
    void removeById(long id);
    
    void removeByRoleId(long roleId);
    
    boolean exists(long userId, long roleId);
    
    UserRoleLink get(long userId, long roleId);

    List<UserRoleLink> listByUserId(long userId);
    
}
