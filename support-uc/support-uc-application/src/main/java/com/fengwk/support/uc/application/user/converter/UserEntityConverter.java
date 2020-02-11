package com.fengwk.support.uc.application.user.converter;

import com.fengwk.support.uc.api.user.model.UserEntityDTO;
import com.fengwk.support.uc.domain.user.model.User;

/**
 * 
 * @author fengwk
 */
public class UserEntityConverter {
    
    private UserEntityConverter() {}

    public static UserEntityDTO convert(User user) {
        if (user == null) {
            return null;
        }
        UserEntityDTO userEntityDTO = new UserEntityDTO();
        UserConverter.cover(user, userEntityDTO);
        userEntityDTO.setCreatedTime(user.getCreatedTime());
        userEntityDTO.setModifiedTime(user.getModifiedTime());
        return userEntityDTO;
    }
    
}
