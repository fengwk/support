package com.fengwk.support.core.idgen;

/**
 * 
 * @author fengwk
 */
public interface IdGeneratorFactory<ID> {

    IdGenerator<ID> getInstance(Class<?> clazz);
    
}
