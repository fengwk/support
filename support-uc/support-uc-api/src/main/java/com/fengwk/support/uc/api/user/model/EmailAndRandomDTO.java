package com.fengwk.support.uc.api.user.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class EmailAndRandomDTO {

    @NotBlank
    protected String email;
    
    @NotBlank
    protected String random;
    
}
