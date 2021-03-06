package com.fengwk.support.uc.api.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class EmailAndRandomResetPasswordResetDTO {

    @NotBlank
    String email;
    
    @NotBlank
    String random;
    
    @NotBlank
    String newPassword;
    
    @NotBlank
    String responseType = "code";
    
    @NotNull
    Long clientId;
    
    @NotBlank
    String redirectUri;
    
    @NotBlank
    String scope = "normal";
    
    String state;
    
}
