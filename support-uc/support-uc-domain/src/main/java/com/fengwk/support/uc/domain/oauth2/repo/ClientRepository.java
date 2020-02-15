package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.uc.domain.oauth2.model.Client;

/**
 * 
 * @author fengwk
 */
public interface ClientRepository {

    void add(Client client);
    
    void removeById(long id);
    
    void updateById(Client client);
    
    Client getById(long id);
    
    Page<Client> page(Query<Client> query, PageQuery pageQuery);
    
}
