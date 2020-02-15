package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public MysqlRolePermissionLinkRepository(RolePermissionLinkMapper rolePermissionLinkMapper, RolePermissionLinkConverter rolePermissionLinkConverter) {
        super(rolePermissionLinkMapper, rolePermissionLinkConverter);
    }
    
    @Override
    public void add(RolePermissionLink rolePermissionLink) {
        mapper().insert(rolePermissionLink);
    }

    @Override
    public void removeById(long id) {
        mapper().deleteById(id);
    }
    
    @Override
    public void removeByRoleId(long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId))
                .build();
        mapper().deleteByExample(example);
    }

    @Override
    public void removeByPermissionId(long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        mapper().deleteByExample(example);
    }

    @Override
    public RolePermissionLink get(long roleId, long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId).andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        return mapper().getByExample(example);
    }

    @Override
    public boolean exists(long roleId, long permissionId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId).andEqualTo(RolePermissionLinkPO::getPermissionId, permissionId))
                .build();
        return mapper().countByExample(example) > 0;
    }

    @Override
    public List<RolePermissionLink> listByRoleId(long roleId) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePermissionLinkPO::getRoleId, roleId))
                .build();
        return mapper().listByExample(example);
    }

    @Override
    public List<RolePermissionLink> listByRoleIds(Collection<Long> roleIds) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andIn(RolePermissionLinkPO::getRoleId, roleIds))
                .build();
        return mapper().listByExample(example);
    }

}
