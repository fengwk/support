package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.uc.domain.oauth2.model.Client;

/**
 * 
 * @author fengwk
 */
public interface ClientRepository {

    void add(Client client);
    
    Client getById(long id);
    
}
