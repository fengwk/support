package com.fengwk.support.core.convention.idgen;

/**
 * 
 * @author fengwk
 */
public class DummyIdGenerator<ID> implements IdGenerator<ID> {

    @Override
    public ID next() {
        return null;
    }
    
}
