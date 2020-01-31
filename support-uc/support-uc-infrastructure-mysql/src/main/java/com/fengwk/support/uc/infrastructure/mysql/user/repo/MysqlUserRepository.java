package com.fengwk.support.uc.infrastructure.mysql.user.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.user.converter.UserConverter;
import com.fengwk.support.uc.infrastructure.mysql.user.mapper.UserMapper;
import com.fengwk.support.uc.infrastructure.mysql.user.model.UserPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlUserRepository extends UcMysqlRepository<User, UserPO> implements UserRepository {

    @Autowired
    volatile UserMapper userMapper;
    
    @Autowired
    volatile UserConverter userConverter;

    @Override
    protected BasicMapper<UserPO, Long> mapper() {
        return userMapper;
    }

    @Override
    protected Converter<User, UserPO, Long> converter() {
        return userConverter;
    }
    
    @Override
    public void add(User user) {
        mapperConvention().insert(user);
    }

    @Override
    public void update(User user) {
        mapperConvention().updateById(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserPO::getEmail, email))
                .build();
        return mapperConvention().countByExample(example) > 0;
    }

    @Override
    public User getByEmail(String email) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserPO::getEmail, email))
                .build();
        return mapperConvention().getByExample(example);
    }

}
