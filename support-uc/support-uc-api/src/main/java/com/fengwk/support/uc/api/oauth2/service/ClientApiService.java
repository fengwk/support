package com.fengwk.support.uc.api.oauth2.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.uc.api.oauth2.model.ClientCreateDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientSearchDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientUpdateDTO;

/**
 * 
 * @author fengwk
 */
public interface ClientApiService {

    ClientDTO create(@NotNull @Valid ClientCreateDTO createDTO);
    
    void remove(long clientId);
    
    ClientDTO updateSelective(@NotNull @Valid ClientUpdateDTO updateDTO);
    
    String refreshSecret(long clientId);
    
    Page<ClientDTO> search(@NotNull @Valid ClientSearchDTO searchDTO);
    
}
