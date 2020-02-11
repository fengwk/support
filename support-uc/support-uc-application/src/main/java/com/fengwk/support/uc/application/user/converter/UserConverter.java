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
        cover(user, userDTO);
        return userDTO;
    }
    
    static void cover(User src, UserDTO dest) {
        if (src == null || dest == null) {
            return;
        }
        dest.setEmail(src.getEmail());
        dest.setId(src.getId());
        dest.setNickname(src.getNickname());
    }
    
}
