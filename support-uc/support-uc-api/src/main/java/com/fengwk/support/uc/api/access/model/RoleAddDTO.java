package com.fengwk.support.uc.api.access.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class RoleAddDTO {

    @NotBlank
    String name;
    
}
