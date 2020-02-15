package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.List;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author fengwk
 */
public interface ConventionMapper<E extends BasicEntity<I>, P extends BasicPO<I>, I> {

    List<E> listAll();

    E getById(I id);
    
    List<E> listByIds(Iterable<I> ids);

    boolean existsById(I id);

    int insert(E entity);

    int insertSelective(E entity);

    int updateById(E entity);

    int updateByIdSelective(E entity);

    int deleteById(I id);

    List<E> listByExample(Example example);

    E getByExample(Example example);
    
    int countByExample(Example example);
    
    int deleteByExample(Example example);

    int updateByExample(E entity, Example example);

    int updateByExampleSelective(E entity, Example example);
    
    Page<E> page(PageQuery pageQuery);
    
    Page<E> page(PageQuery pageQuery, boolean isCountTotal);

    Page<E> pageByExample(Example example, PageQuery pageQuery);

    Page<E> pageByExample(Example example, PageQuery pageQuery, boolean isCountTotal);

}
