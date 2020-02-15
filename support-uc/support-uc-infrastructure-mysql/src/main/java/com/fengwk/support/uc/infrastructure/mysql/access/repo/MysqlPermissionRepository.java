package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.spring.boot.starter.mysql.convention.PropertyMapper;
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
    public void removeById(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void updateById(Permission permission) {
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
    public Permission getById(long id) {
        return mapper().getById(id);
    }

    @Override
    public List<Permission> listByIds(Collection<Long> ids) {
        return mapper().listByIds(ids);
    }

    @Override
    public Page<Permission> page(Query<Permission> query, PageQuery pageQuery) {
        return mapper().pageByQuery(query, pageQuery);
    }

    @Override
    protected void register(PropertyMapper<Permission, PermissionPO> propertyMapper) {
        super.register(propertyMapper);
        propertyMapper.register(Property.of(Permission::getName), Property.of(PermissionPO::getName));
    }
    
}
