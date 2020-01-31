package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
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
    volatile PermissionMapper permissionMapper;
    
    @Autowired
    volatile PermissionConverter permissionConverter;

    @Override
    protected BasicMapper<PermissionPO, Long> mapper() {
        return permissionMapper;
    }

    @Override
    protected Converter<Permission, PermissionPO, Long> converter() {
        return permissionConverter;
    }
    
    @Override
    public void add(Permission permission) {
        mapperConvention().insert(permission);
    }

    @Override
    public void remove(long id) {
        mapperConvention().deleteById(id);
    }

    @Override
    public void update(Permission permission) {
        mapperConvention().updateById(permission);
    }
    
    @Override
    public boolean existsByName(String name) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(PermissionPO::getName, name))
                .build();
        return mapperConvention().countByExample(example) > 0;
    }

    @Override
    public Permission get(long id) {
        return mapperConvention().getById(id);
    }

    @Override
    public List<Permission> list(Collection<Long> ids) {
        return mapperConvention().listById(ids);
    }

    @Override
    public Page<Permission> page(PageQuery pageQuery) {
        return mapperConvention().page(pageQuery);
    }

}
