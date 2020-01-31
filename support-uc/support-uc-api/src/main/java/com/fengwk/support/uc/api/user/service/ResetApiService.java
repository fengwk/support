package com.fengwk.support.uc.api.user.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomResetPasswordResetDTO;

/**
 * 
 * @author fengwk
 */
public interface ResetApiService {
    
    int sendEmailRandom(@NotBlank String email);
    
    boolean verifyEmailRandom(@NotNull @Valid EmailAndRandomDTO emailAndRandomDTO);
    
    String resetPasswordAndAuthorize(@NotNull @Valid EmailAndRandomResetPasswordResetDTO resetDTO);
    
}
