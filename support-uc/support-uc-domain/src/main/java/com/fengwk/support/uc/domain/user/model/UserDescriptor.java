package com.fengwk.support.uc.domain.user.model;

import com.fengwk.support.core.domain.model.ValueObject;

import lombok.Data;

/**
 * 用户描述符
 * 
 * @author fengwk
 */
@Data
public class UserDescriptor implements ValueObject {

    final long userId;
    final String email;
    final String nickname;
    
    UserDescriptor(long userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }
    
}