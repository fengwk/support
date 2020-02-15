package com.fengwk.support.core.convention.idgen;

/**
 * 
 * @author fengwk
 */
public interface IdGeneratorFactory<ID> {

    IdGenerator<ID> getInstance(Class<?> clazz);
    
}
