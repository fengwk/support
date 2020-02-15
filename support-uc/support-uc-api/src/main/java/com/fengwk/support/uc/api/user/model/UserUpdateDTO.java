package com.fengwk.support.uc.api.user.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class UserUpdateDTO {
    
    @NotNull
    private Long id;
    
    private String email;
    
    private String nickname;
    
}
