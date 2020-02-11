package com.fengwk.support.uc.domain.user.model;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class UserCreateInfo {

    private final String email;
    private final String nickname;
    private final String cleartextPassword;
    
}
