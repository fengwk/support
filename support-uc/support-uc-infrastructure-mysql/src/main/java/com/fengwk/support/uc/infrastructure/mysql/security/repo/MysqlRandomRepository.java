package com.fengwk.support.uc.infrastructure.mysql.security.repo;

import org.springframework.stereotype.Repository;

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

    public MysqlRandomRepository(RandomMapper randomMapper, RandomConverter randomConverter) {
        super(randomMapper, randomConverter);
    }
    
    @Override
    public void add(Random random) {
        mapper().insert(random);
    }

    @Override
    public void updateById(Random random) {
        mapper().updateById(random);
    }

    @Override
    public Random get(Way way, Type type, String target) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RandomPO::getWay, way.getCode()).andEqualTo(RandomPO::getType, type.getCode()).andEqualTo(RandomPO::getTarget, target))
                .build();
        return mapper().getByExample(example);
    }

}
