package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.List;

import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.query.Query;
import com.fengwk.support.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
public class QueryConventionMapperImpl<E extends BasicEntity<I>, P extends BasicPO<I>, I> implements QueryConventionMapper<E, P, I> {

    final ConventionMapper<E, P, I> conventionMapper;
    final Class<P> poClass;
    final PropertyMapper propertyMapper;
    
    QueryConventionMapperImpl(ConventionMapper<E, P, I> conventionMapper, PropertyMapper propertyMapper, Class<P> poClass) {
        Preconditions.notNull(conventionMapper, "ConventionMapper cannot be null.");
        Preconditions.notNull(propertyMapper, "PropertyMapper cannot be null.");
        Preconditions.notNull(poClass, "POClass cannot be null.");
        this.conventionMapper = conventionMapper;
        this.poClass = poClass;
        this.propertyMapper = propertyMapper;
    }
    
    @Override
    public List<E> listAll() {
        return conventionMapper.listAll();
    }

    @Override
    public E getById(I id) {
        return conventionMapper.getById(id);
    }

    @Override
    public List<E> listById(Iterable<I> ids) {
        return conventionMapper.listById(ids);
    }

    @Override
    public boolean existsById(I id) {
        return conventionMapper.existsById(id);
    }

    @Override
    public int insert(E entity) {
        return conventionMapper.insert(entity);
    }

    @Override
    public int insertSelective(E entity) {
        return conventionMapper.insertSelective(entity);
    }

    @Override
    public int updateById(E entity) {
        return conventionMapper.updateById(entity);
    }

    @Override
    public int updateByIdSelective(E entity) {
        return conventionMapper.updateByIdSelective(entity);
    }

    @Override
    public int deleteById(I id) {
        return conventionMapper.deleteById(id);
    }

    @Override
    public List<E> listByExample(Example example) {
        return conventionMapper.listByExample(example);
    }

    @Override
    public E getByExample(Example example) {
        return conventionMapper.getByExample(example);
    }

    @Override
    public int countByExample(Example example) {
        return conventionMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(Example example) {
        return conventionMapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(E entity, Example example) {
        return conventionMapper.updateByExample(entity, example);
    }

    @Override
    public int updateByExampleSelective(E entity, Example example) {
        return conventionMapper.updateByExampleSelective(entity, example);
    }

    @Override
    public Page<E> page(PageQuery pageQuery) {
        return conventionMapper.page(pageQuery);
    }

    @Override
    public Page<E> page(PageQuery pageQuery, boolean countTotal) {
        return conventionMapper.page(pageQuery, countTotal);
    }

    @Override
    public Page<E> pageByExample(Example example, PageQuery pageQuery) {
        return conventionMapper.pageByExample(example, pageQuery);
    }

    @Override
    public Page<E> pageByExample(Example example, PageQuery pageQuery, boolean countTotal) {
        return conventionMapper.pageByExample(example, pageQuery, countTotal);
    }

    @Override
    public List<E> listByQuery(Query<E> query) {
        return conventionMapper.listByExample(adaptQuery(query));
    }

    @Override
    public E getByQuery(Query<E> query) {
        return conventionMapper.getByExample(adaptQuery(query));
    }

    @Override
    public int countByQuery(Query<E> query) {
        return conventionMapper.countByExample(adaptQuery(query));
    }

    @Override
    public Page<E> pageByQuery(Query<E> query, PageQuery pageQuery) {
        return conventionMapper.pageByExample(adaptQuery(query), pageQuery);
    }

    @Override
    public Page<E> pageByQuery(Query<E> query, PageQuery pageQuery, boolean countTotal) {
        return conventionMapper.pageByExample(adaptQuery(query), pageQuery, countTotal);
    }

    private Example adaptQuery(Query<E> query) {
        QueryAdapter<E, P, I> queryAdapter = new QueryAdapter<>(query, propertyMapper, poClass);
        return queryAdapter.adapt();
    }
    
}
