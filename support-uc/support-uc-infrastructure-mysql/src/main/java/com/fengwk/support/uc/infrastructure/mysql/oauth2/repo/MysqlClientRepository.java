package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
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
    volatile ClientMapper clientMapper;
    
    @Autowired
    volatile ClientConverter clientConverter;

    @Override
    protected BasicMapper<ClientPO, Long> mapper() {
        return clientMapper;
    }

    @Override
    protected Converter<Client, ClientPO, Long> converter() {
        return clientConverter;
    }
    
    @Override
    public void add(Client client) {
        mapperConvention().insert(client);
    }
    
    @Override
    public Client get(long id) {
        return mapperConvention().getById(id);
    }

}
