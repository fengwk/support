package com.fengwk.support.core.convention.idgen;

/**
 * 
 * @author fengwk
 */
public interface IdGenerators<ID> {

    IdGenerator<ID> get(String generatorName);
    
}
