package com.fengwk.support.uc.api.access.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class PermissionUpdateDTO {

    @NotNull
    Long id;
    
    @NotBlank
    String name;
    
    @NotBlank
    String path;
    
}
