package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.uc.domain.access.model.Permission;
import com.fengwk.support.uc.domain.access.repo.PermissionRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.access.converter.PermissionConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.mapper.PermissionMapper;
import com.fengwk.support.uc.infrastructure.mysql.access.model.PermissionPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlPermissionRepository extends UcMysqlRepository<Permission, PermissionPO> implements PermissionRepository {

    @Autowired
    public MysqlPermissionRepository(PermissionMapper permissionMapper, PermissionConverter permissionConverter) {
        super(permissionMapper, permissionConverter);
    }
    
    @Override
    public void add(Permission permission) {
        mapper().insert(permission);
    }

    @Override
    public void remove(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void update(Permission permission) {
        mapper().updateById(permission);
    }
    
    @Override
    public boolean existsByName(String name) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(PermissionPO::getName, name))
                .build();
        return mapper().countByExample(example) > 0;
    }

    @Override
    public Permission get(long id) {
        return mapper().getById(id);
    }

    @Override
    public List<Permission> list(Collection<Long> ids) {
        return mapper().listById(ids);
    }

    @Override
    public Page<Permission> page(PageQuery pageQuery) {
        return mapper().page(pageQuery);
    }

}
