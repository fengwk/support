package com.fengwk.support.uc.domain.security.repo;

import com.fengwk.support.uc.domain.security.model.Random;

/**
 * 
 * @author fengwk
 */
public interface RandomRepository {

    void add(Random random);
    
    void update(Random random);
    
    Random get(Random.Way way, Random.Type type, String target);
    
}
