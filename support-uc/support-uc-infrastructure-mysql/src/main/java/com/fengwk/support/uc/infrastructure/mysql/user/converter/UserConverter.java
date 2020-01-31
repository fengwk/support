package com.fengwk.support.uc.infrastructure.mysql.user.converter;

import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.user.model.UserPO;

/**
 * 
 * @author fengwk
 */
@Component
public class UserConverter implements UcConverter<User, UserPO> {

    @Override
    public UserPO convert(User entity) {
        if (entity == null) {
            return null;
        }
        UserPO userPO = new UserPO();
        userPO.setCreatedTime(entity.getCreatedTime());
        userPO.setEmail(entity.getEmail());
        userPO.setId(entity.getId());
        userPO.setModifiedTime(entity.getModifiedTime());
        userPO.setNickname(entity.getNickname());
        userPO.setPassword(entity.getPassword());
        return userPO;
    }

    @Override
    public User convert(UserPO po) {
        if (po == null) {
            return null;
        }
        User user = new User();
        user.setCreatedTime(po.getCreatedTime());
        user.setEmail(po.getEmail());
        user.setId(po.getId());
        user.setModifiedTime(po.getModifiedTime());
        user.setNickname(po.getNickname());
        user.setPassword(po.getPassword());
        return user;
    }
    
}
