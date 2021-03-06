package com.fengwk.support.uc.api.access.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class PermissionCreateDTO {

    @NotBlank
    String name;
    
    @NotBlank
    String path;
    
}
