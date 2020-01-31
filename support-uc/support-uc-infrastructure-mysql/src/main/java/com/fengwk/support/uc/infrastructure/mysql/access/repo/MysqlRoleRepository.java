package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.access.model.Role;
import com.fengwk.support.uc.domain.access.repo.RoleRepository;
import com.fengwk.support.uc.infrastructure.mysql.UcMysqlRepository;
import com.fengwk.support.uc.infrastructure.mysql.access.converter.RoleConverter;
import com.fengwk.support.uc.infrastructure.mysql.access.mapper.RoleMapper;
import com.fengwk.support.uc.infrastructure.mysql.access.model.RolePO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
@Repository
public class MysqlRoleRepository extends UcMysqlRepository<Role, RolePO> implements RoleRepository {

    @Autowired
    volatile RoleMapper roleMapper;
    
    @Autowired
    volatile RoleConverter roleConverter;

    @Override
    protected BasicMapper<RolePO, Long> mapper() {
        return roleMapper;
    }

    @Override
    protected Converter<Role, RolePO, Long> converter() {
        return roleConverter;
    }
    
    @Override
    public void add(Role role) {
        mapperConvention().insert(role);
    }

    @Override
    public void remove(long id) {
        mapperConvention().deleteById(id);
    }

    @Override
    public void update(Role role) {
        mapperConvention().updateById(role);
    }
    
    @Override
    public boolean existsByName(String name) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePO::getName, name))
                .build();
        return mapperConvention().countByExample(example) > 0;
    }

    @Override
    public Role get(long id) {
        return mapperConvention().getById(id);
    }

    @Override
    public List<Role> list(Collection<Long> ids) {
        return mapperConvention().listById(ids);
    }

    @Override
    public Page<Role> page(PageQuery pageQuery) {
        return mapperConvention().page(pageQuery);
    }

}
