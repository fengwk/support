package com.fengwk.support.uc.api.access.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class RoleDTO {

    private Long id;
    private String name;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

}
