package com.fengwk.support.uc.infrastructure.mysql.access.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
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
    public MysqlRoleRepository(RoleMapper roleMapper, RoleConverter roleConverter) {
        super(roleMapper, roleConverter);
    }

    @Override
    public void add(Role role) {
        mapper().insert(role);
    }

    @Override
    public void remove(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void update(Role role) {
        mapper().updateById(role);
    }
    
    @Override
    public boolean existsByName(String name) {
        Example example = exampleBuilder()
                .andWhere(weekendSqls().andEqualTo(RolePO::getName, name))
                .build();
        return mapper().countByExample(example) > 0;
    }

    @Override
    public Role get(long id) {
        return mapper().getById(id);
    }

    @Override
    public List<Role> list(Collection<Long> ids) {
        return mapper().listById(ids);
    }

    @Override
    public Page<Role> page(PageQuery pageQuery) {
        return mapper().page(pageQuery);
    }

}
