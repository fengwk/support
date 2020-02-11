package com.fengwk.support.uc.domain.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.domain.exception.DomainException;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class RandomCheckService {

    @Autowired
    volatile RandomRepository randomRepository;
    
    public Random checkValueWithUnusedAndGet(Random.Way way, Random.Type type, String target, String value) {
        Random random = getRequiredNonNull(way, type, target);
        if (random.isExpired()) {
            log.warn("验证码已过期,way={},type={},target={}.", way, type, target);
            throw new DomainException("验证码已过期");
        }
        if (!random.isUnused()) {
            log.warn("验证码无效,way={},type={},target={}.", way, type, target);
            throw new DomainException("验证码无效");
        }
        if (!random.verify(value)) {
            log.warn("验证码错误,way={},type={},target={}.", way, type, target);
            throw new DomainException("验证码错误");
        }
        return random;
    }
    
    public Random checkValueWithSilencedAndGet(Random.Way way, Random.Type type, String target, String value) {
        Random random = getRequiredNonNull(way, type, target);
        if (!random.isSilenced()) {
            log.warn("验证码无效,way={},type={},target={}.", way, type, target);
            throw new DomainException("验证码无效");
        }
        if (!random.verify(value)) {
            log.warn("验证码错误,way={},type={},target={}.", way, type, target);
            throw new DomainException("验证码错误");
        }
        return random;
    }
    
    private Random getRequiredNonNull(Random.Way way, Random.Type type, String target) {
        Random random = randomRepository.get(way, type, target);
        if (random == null) {
            log.warn("验证码不存在, way={}, type={}, target={}.", way, type, target);
            throw new DomainException("验证码不存在");
        }
        return random;
    }
    
}
