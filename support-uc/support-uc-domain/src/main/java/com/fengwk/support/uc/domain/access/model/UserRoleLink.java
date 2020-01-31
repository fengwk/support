package com.fengwk.support.uc.domain.access.model;

import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleLink extends UcEntity {
    
    long userId;
    long roleId;
    
    public static UserRoleLink of(long userId, long roleId) {
        UserRoleLink userRoleLink = new UserRoleLink();
        userRoleLink.userId = userId;
        userRoleLink.roleId = roleId;
        return userRoleLink;
    }
    
}
