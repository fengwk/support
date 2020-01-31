package com.fengwk.support.core.idgen;

/**
 * 
 * @author fengwk
 */
public interface IdGenerators<ID> {

    IdGenerator<ID> get(String generatorName);
    
}
