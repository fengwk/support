package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.access.model.UserRoleLink;
import com.fengwk.support.uc.domain.access.repo.UserRoleLinkRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.access.converter.UserRoleLinkConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.mapper.UserRoleLinkMapper;
import com.fengwk.support.uc.infrastructure.mysql.access.model.UserRoleLinkPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlUserRoleLinkRepository extends UcMysqlRepository<UserRoleLink, UserRoleLinkPO> implements UserRoleLinkRepository {

    @Autowired
    volatile UserRoleLinkMapper userRoleLinkMapper;

    @Autowired
    volatile UserRoleLinkConverter userRoleLinkConverter;
    
    @Override
    protected BasicMapper<UserRoleLinkPO, Long> mapper() {
        return userRoleLinkMapper;
    }

    @Override
    protected Converter<UserRoleLink, UserRoleLinkPO, Long> converter() {
        return userRoleLinkConverter;
    }
    
    @Override
    public void add(UserRoleLink userRoleLink) {
        mapperConvention().insert(userRoleLink);
    }

    @Override
    public void remove(long id) {
        mapperConvention().deleteById(id);
    }

    @Override
    public void removeByRoleId(long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        mapperConvention().deleteByExample(example);
    }

    @Override
    public boolean exists(long userId, long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId).andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        return mapperConvention().countByExample(example) > 0;
    }

    @Override
    public UserRoleLink get(long userId, long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId).andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        return mapperConvention().getByExample(example);
    }

    @Override
    public List<UserRoleLink> listByUserId(long userId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId))
                .build();
        return mapperConvention().listByExample(example);
    }

}
