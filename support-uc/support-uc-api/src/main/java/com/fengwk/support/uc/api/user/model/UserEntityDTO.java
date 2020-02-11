package com.fengwk.support.uc.api.user.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 * @since 2020-02-05 14:35
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UserEntityDTO extends UserDTO {

    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
}
