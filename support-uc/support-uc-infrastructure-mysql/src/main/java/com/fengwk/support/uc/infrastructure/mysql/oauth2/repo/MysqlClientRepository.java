package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.spring.boot.starter.mysql.convention.PropertyMapper;
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
    public void removeById(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void updateById(Client client) {
        mapper().updateById(client);
    }
    
    @Override
    public Client getById(long id) {
        return mapper().getById(id);
    }

    @Override
    public Page<Client> page(Query<Client> query, PageQuery pageQuery) {
        return mapper().pageByQuery(query, pageQuery);
    }

    @Override
    protected void register(PropertyMapper<Client, ClientPO> propertyMapper) {
        super.register(propertyMapper);
        propertyMapper.register(Property.of(Client::getName), Property.of(ClientPO::getName));
    }
    
}
