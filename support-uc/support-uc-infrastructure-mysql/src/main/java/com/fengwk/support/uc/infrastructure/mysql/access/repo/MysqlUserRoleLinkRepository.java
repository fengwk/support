package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public MysqlUserRoleLinkRepository(UserRoleLinkMapper userRoleLinkMapper, UserRoleLinkConverter userRoleLinkConverter) {
        super(userRoleLinkMapper, userRoleLinkConverter);
    }
    
    @Override
    public void add(UserRoleLink userRoleLink) {
        mapper().insert(userRoleLink);
    }

    @Override
    public void remove(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void removeByRoleId(long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        mapper().deleteByExample(example);
    }

    @Override
    public boolean exists(long userId, long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId).andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        return mapper().countByExample(example) > 0;
    }

    @Override
    public UserRoleLink get(long userId, long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId).andEqualTo(UserRoleLinkPO::getRoleId, roleId))
                .build();
        return mapper().getByExample(example);
    }

    @Override
    public List<UserRoleLink> listByUserId(long userId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(UserRoleLinkPO::getUserId, userId))
                .build();
        return mapper().listByExample(example);
    }

}
