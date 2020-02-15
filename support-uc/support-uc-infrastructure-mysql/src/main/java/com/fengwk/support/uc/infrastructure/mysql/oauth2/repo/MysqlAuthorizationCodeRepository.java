package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public MysqlAuthorizationCodeRepository(AuthorizationCodeMapper authorizationCodeMapper, AuthorizationCodeConverter authorizationCodeConverter) {
        super(authorizationCodeMapper, authorizationCodeConverter);
    }

    @Override
    public void add(AuthorizationCode authCode) {
        mapper().insert(authCode);
    }

    @Override
    public void updateById(AuthorizationCode authCode) {
        mapper().updateById(authCode);
    }

    @Override
    public AuthorizationCode getByCode(String code) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(AuthorizationCodePO::getCode, code))
                .build();
        return mapper().getByExample(example);
    }

}
