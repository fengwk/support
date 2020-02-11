package com.fengwk.support.spring.boot.starter.mysql;

import com.fengwk.support.domain.model.BasicEntity;

/**
 * 
 * @author fengwk
 */
public interface BasicConverter<E extends BasicEntity<I>, P extends BasicPO<I>, I> {

    P convert(E entity);
    
    E convert(P po);
    
}
