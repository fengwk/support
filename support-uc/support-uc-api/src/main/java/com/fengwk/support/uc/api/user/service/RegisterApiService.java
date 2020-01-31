package com.fengwk.support.uc.api.user.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomRegisterDTO;

/**
 * 
 * @author fengwk
 */
public interface RegisterApiService {

    int sendEmailRandom(@NotBlank String email);
    
    boolean verifyEmailRandom(@NotNull @Valid EmailAndRandomDTO emailAndRandomDTO);
    
    String registerAndAuthorize(@NotNull @Valid EmailAndRandomRegisterDTO registerDTO);
    
}
