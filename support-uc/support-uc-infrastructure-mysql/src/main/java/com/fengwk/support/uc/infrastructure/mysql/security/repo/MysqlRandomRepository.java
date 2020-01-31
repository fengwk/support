package com.fengwk.support.uc.infrastructure.mysql.security.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.model.Random.Type;
import com.fengwk.support.uc.domain.security.model.Random.Way;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.security.converter.RandomConverter;
import com.fengwk.support.uc.infrastructure.mysql.security.mapper.RandomMapper;
import com.fengwk.support.uc.infrastructure.mysql.security.model.RandomPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlRandomRepository extends UcMysqlRepository<Random, RandomPO> implements RandomRepository {

    @Autowired
    volatile RandomMapper randomMapper;
    
    @Autowired
    volatile RandomConverter randomConverter;

    @Override
    protected BasicMapper<RandomPO, Long> mapper() {
        return randomMapper;
    }

    @Override
    protected Converter<Random, RandomPO, Long> converter() {
        return randomConverter;
    }
    
    @Override
    public void add(Random random) {
        mapperConvention().insert(random);
    }

    @Override
    public void update(Random random) {
        mapperConvention().updateById(random);
    }

    @Override
    public Random get(Way way, Type type, String target) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RandomPO::getWay, way.getCode()).andEqualTo(RandomPO::getType, type.getCode()).andEqualTo(RandomPO::getTarget, target))
                .build();
        return mapperConvention().getByExample(example);
    }

}
