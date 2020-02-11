package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.converter.ClientConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.mapper.ClientMapper;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.ClientPO;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlClientRepository extends UcMysqlRepository<Client, ClientPO> implements ClientRepository {

    @Autowired
    public MysqlClientRepository(ClientMapper clientMapper, ClientConverter clientConverter) {
        super(clientMapper, clientConverter);
    }

    @Override
    public void add(Client client) {
        mapper().insert(client);
    }
    
    @Override
    public Client getById(long id) {
        return mapper().getById(id);
    }

}
