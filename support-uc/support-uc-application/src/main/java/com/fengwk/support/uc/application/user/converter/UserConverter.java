package com.fengwk.support.uc.application.user.converter;

import com.fengwk.support.uc.api.user.model.UserDTO;
import com.fengwk.support.uc.domain.user.model.User;

/**
 * 
 * @author fengwk
 */
public class UserConverter {
    
    private UserConverter() {}

    public static UserDTO convert(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNickname(user.getNickname());
        return userDTO;
    }
    
}
