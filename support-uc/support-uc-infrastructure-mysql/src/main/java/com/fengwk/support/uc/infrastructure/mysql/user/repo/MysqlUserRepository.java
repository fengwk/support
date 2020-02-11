package com.fengwk.support.uc.infrastructure.mysql.user.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.bean.Property;
import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.query.Query;
import com.fengwk.support.spring.boot.starter.mysql.convention.PropertyMapper;
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
    public MysqlUserRepository(UserMapper userMapper, UserConverter userConverter) {
        super(userMapper, userConverter);
    }

    @Override
    public void add(User user) {
        mapper().insert(user);
    }

    @Override
    public void updateById(User user) {
        mapper().updateById(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserPO::getEmail, email))
                .build();
        return mapper().countByExample(example) > 0;
    }
    
    @Override
    public User getById(long id) {
        return mapper().getById(id);
    }

    @Override
    public User getByEmail(String email) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserPO::getEmail, email))
                .build();
        return mapper().getByExample(example);
    }

    @Override
    public Page<User> page(Query<User> query, PageQuery pageQuery) {
        return mapper().pageByQuery(query, pageQuery);
    }

    @Override
    protected void register(PropertyMapper propertyMapper) {
        super.register(propertyMapper);
        propertyMapper.register(Property.of(User::getEmail), Property.of(UserPO::getEmail));
        propertyMapper.register(Property.of(User::getNickname), Property.of(UserPO::getNickname));
    }

}
