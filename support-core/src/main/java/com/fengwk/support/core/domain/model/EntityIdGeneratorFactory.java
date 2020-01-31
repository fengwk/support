package com.fengwk.support.core.domain.model;

import com.fengwk.support.core.idgen.IdGenerator;

/**
 * 
 * @author fengwk
 */
public interface EntityIdGeneratorFactory {

    <I> IdGenerator<I> getInstance(Class<? extends Entity<I>> entityClass);
    
}
