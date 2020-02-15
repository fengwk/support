package com.fengwk.support.uc.api.user.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class UserDTO {
    
    private Long id;
    private String email;
    private String nickname;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
}
