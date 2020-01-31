package com.fengwk.support.core.idgen;

/**
 * 
 * @author fengwk
 */
public class SingletonIdGenerators<ID> implements IdGenerators<ID> {

    private final IdGenerator<ID> idGenerator;
    
    public SingletonIdGenerators(IdGenerator<ID> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public IdGenerator<ID> get(String generatorName) {
        return idGenerator;
    }
    
}
