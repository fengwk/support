package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.List;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.query.Query;
import com.fengwk.support.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

/**
 * 
 * @author fengwk
 */
public interface QueryConventionMapper<E extends BasicEntity<I>, P extends BasicPO<I>, I> extends ConventionMapper<E, P, I> {

    List<E> listByQuery(Query<E> query);
    
    E getByQuery(Query<E> query);
    
    int countByQuery(Query<E> query);
    
    Page<E> pageByQuery(Query<E> query, PageQuery pageQuery);

    Page<E> pageByQuery(Query<E> query, PageQuery pageQuery, boolean countTotal);
    
}
