package com.fengwk.support.uc.domain.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
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
public class RandomService {

    @Autowired
    volatile RandomRepository randomRepository;
    
    public Random checkValueWithIsUnusedAndGet(Random.Way way, Random.Type type, String target, String testValue) {
        Random random = checkout(way, type, target);
        if (random.isExpired()) {
            log.warn("验证码已过期,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码已过期");
        }
        if (!random.isUnused()) {
            log.warn("验证码无效,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码无效");
        }
        if (!random.verify(testValue)) {
            log.warn("验证码错误,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码错误");
        }
        return random;
    }
    
    public Random checkValueWithIsSilencedAndGet(Random.Way way, Random.Type type, String target, String testValue) {
        Random random = checkout(way, type, target);
        if (!random.isSilenced()) {
            log.warn("验证码无效,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码无效");
        }
        if (!random.verify(testValue)) {
            log.warn("验证码错误,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码错误");
        }
        return random;
    }
    
    private Random checkout(Random.Way way, Random.Type type, String target) {
        Random random = randomRepository.get(way, type, target);
        if (random == null) {
            log.warn("验证码不存在,way={},type={},target={}.", way, type, target);
            throw ExceptionCodes.biz().create("验证码不存在");
        }
        return random;
    }
    
}
