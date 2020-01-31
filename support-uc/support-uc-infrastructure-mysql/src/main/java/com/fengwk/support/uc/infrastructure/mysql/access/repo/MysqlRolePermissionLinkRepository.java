package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.access.model.RolePermissionLink;
import com.fengwk.support.uc.domain.access.repo.RolePermissionLinkRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.access.converter.RolePermissionLinkConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.mapper.RolePermissionLinkMapper;
import com.fengwk.support.uc.infrastructure.mysql.access.model.RolePermissionLinkPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlRolePermissionLinkRepository extends UcMysqlRepository<RolePermissionLink, RolePermissionLinkPO> implements RolePermissionLinkRepository {

    @Autowired
    volatile RolePermissionLinkMapper rolePermissionLinkMapper;

    @Autowired
    volatile RolePermissionLinkConverter rolePermissionLinkConverter;
    
    @Override
    protected BasicMapper<RolePermissionLinkPO, Long> mapper() {
        return rolePermissionLinkMapper;
    }

    @Override
    protected Converter<RolePermissionLink, RolePermissionLinkPO, Long> converter() {
        return rolePermissionLinkConverter;
    }
    
    @Override
    public void add(RolePermissionLink rolePermissionLink) {
        mapperConvention().insert(rolePermissionLink);
    }

    @Override
    public void remove(long id) {
        mapperConvention().deleteById(id);
    }

    @Override
    public void removeByPermissionId(long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        mapperConvention().deleteByExample(example);
    }

    @Override
    public RolePermissionLink get(long roleId, long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId).andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        return mapperConvention().getByExample(example);
    }

    @Override
    public boolean exists(long roleId, long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId).andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        return mapperConvention().countByExample(example) > 0;
    }

    @Override
    public List<RolePermissionLink> listByRoleId(long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId))
                .build();
        return mapperConvention().listByExample(example);
    }

    @Override
    public List<RolePermissionLink> listByRoleIds(Collection<Long> roleIds) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andIn(RolePermissionLinkPO::getRoleId, roleIds))
                .build();
        return mapperConvention().listByExample(example);
    }

}
