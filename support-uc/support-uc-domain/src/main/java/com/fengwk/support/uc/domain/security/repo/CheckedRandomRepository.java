package com.fengwk.support.uc.domain.security.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.security.model.Random;

/**
 * 
 * @author fengwk
 */
public class CheckedRandomRepository extends CheckedRepository<Random, RandomRepository> {

    public CheckedRandomRepository(RandomRepository repository) {
        super(repository);
    }
    
    public Random get(Random.Way way, Random.Type type, String target) {
        Random random = repository().get(way, type, target);
        checkAndThrowIfNecessary(random, () -> args(arg("way", way), arg("type", type), arg("target", target)));
        return random;
    }
    
    public CheckedRandomRepository requiredNonNull() {
        appendChecker(random -> random == null ? failure("验证码不存在") : success());
        return this;
    }

}
