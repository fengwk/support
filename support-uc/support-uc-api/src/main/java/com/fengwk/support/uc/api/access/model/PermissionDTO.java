package com.fengwk.support.uc.api.access.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class PermissionDTO {

    private Long id;
    private String name;
    private String path;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
}
