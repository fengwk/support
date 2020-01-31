package com.fengwk.support.spring.boot.starter.mysql.convention;

import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

/**
 * 
 * @author fengwk
 */
public interface Converter<E extends BasicEntity<I>, P extends BasicPO<I>, I> {

    P convert(E entity);
    
    E convert(P po);
    
}
