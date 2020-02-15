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
    public void removeById(long id) {
        mapper().deleteById(id);
    }

    @Override
    public void updateById(Role role) {
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
    public Role getById(long id) {
        return mapper().getById(id);
    }

    @Override
    public List<Role> listByIds(Collection<Long> ids) {
        return mapper().listByIds(ids);
    }

    @Override
    public Page<Role> page(Query<Role> query, PageQuery pageQuery) {
        return mapper().pageByQuery(query, pageQuery);
    }
    
    @Override
    protected void register(PropertyMapper<Role, RolePO> propertyMapper) {
        super.register(propertyMapper);
        propertyMapper.register(Property.of(Role::getName), Property.of(RolePO::getName));
    }

}
