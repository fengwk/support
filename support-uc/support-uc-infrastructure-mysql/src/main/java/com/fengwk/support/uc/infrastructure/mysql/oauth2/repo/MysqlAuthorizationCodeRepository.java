package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationCodeRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.converter.AuthorizationCodeConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.mapper.AuthorizationCodeMapper;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.AuthorizationCodePO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlAuthorizationCodeRepository extends UcMysqlRepository<AuthorizationCode, AuthorizationCodePO> implements AuthorizationCodeRepository {

    @Autowired
    volatile AuthorizationCodeMapper authorizationCodeMapper;
    
    @Autowired
    volatile AuthorizationCodeConverter authorizationCodeConverter;
    
    @Override
    protected BasicMapper<AuthorizationCodePO, Long> mapper() {
        return authorizationCodeMapper;
    }

    @Override
    protected Converter<AuthorizationCode, AuthorizationCodePO, Long> converter() {
        return authorizationCodeConverter;
    }
    
    @Override
    public void add(AuthorizationCode authCode) {
        mapperConvention().insert(authCode);
    }

    @Override
    public void update(AuthorizationCode authCode) {
        mapperConvention().updateById(authCode);
    }

    @Override
    public AuthorizationCode getByCode(String code) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(AuthorizationCodePO::getCode, code))
                .build();
        return mapperConvention().getByExample(example);
    }

}
