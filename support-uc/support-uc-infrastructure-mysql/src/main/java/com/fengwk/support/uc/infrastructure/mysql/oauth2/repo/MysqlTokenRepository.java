package com.fengwk.support.uc.infrastructure.mysql.oauth2.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.converter.TokenConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.converter.TokenUserIdConvertStrategy;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.mapper.TokenMapper;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.TokenPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlTokenRepository extends UcMysqlRepository<Token, TokenPO> implements TokenRepository {

    @Autowired
    volatile TokenMapper tokenMapper;
    
    @Autowired
    volatile TokenConverter tokenConverter;
    
    @Override
    protected BasicMapper<TokenPO, Long> mapper() {
        return tokenMapper;
    }

    @Override
    protected Converter<Token, TokenPO, Long> converter() {
        return tokenConverter;
    }
    
    @Override
    public void add(Token token) {
        mapperConvention().insert(token);
    }

    @Override
    public void updateIfValid(Token token) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(TokenPO::getId, token.getId()).andEqualTo(TokenPO::getIsInvalid, 0))
                .build();
        mapperConvention().updateByExample(token, example);
    }

    @Override
    public Token getByAccessToken(String accessToken) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(TokenPO::getAccessToken, accessToken))
                .build();
        return mapperConvention().getByExample(example);
    }

    @Override
    public Token getByRefreshToken(String refreshToken) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(TokenPO::getRefreshToken, refreshToken))
                .build();
        return mapperConvention().getByExample(example);
    }

    @Override
    public List<Token> listValid(long clientId, long userId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(TokenPO::getClientId, clientId).andEqualTo(TokenPO::getUserId, userId).andEqualTo(TokenPO::getIsInvalid, 0))
                .build();
        return mapperConvention().listByExample(example);
    }

    @Override
    public List<Token> listValidWithIsNullUserId(long clientId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(TokenPO::getClientId, clientId).andEqualTo(TokenPO::getUserId, TokenUserIdConvertStrategy.toPO(null)).andEqualTo(TokenPO::getIsInvalid, 0))
                .build();
        return mapperConvention().listByExample(example);
    }

}
