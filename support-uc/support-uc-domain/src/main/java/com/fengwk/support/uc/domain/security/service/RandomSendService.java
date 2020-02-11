package com.fengwk.support.uc.domain.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.domain.exception.DomainException;
import com.fengwk.support.uc.domain.security.facade.RandomMessageSender;
import com.fengwk.support.uc.domain.security.model.EmailRandomMessage;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.model.SMSRandomMessage;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class RandomSendService {

    @Autowired
    volatile RandomMessageSender randomMessageFacade;
    
    @Autowired
    volatile RandomRepository randomRepository;
    
    public Random createAndSend(Random.Way way, Random.Type type, String target, int expiresIn) {
        Random existingRandom = randomRepository.get(way, type, target);
        if (existingRandom == null) {
            Random random = Random.of(way, type, target, expiresIn);
            send(random);
            randomRepository.add(random);
            return random;
        } else {
            if (!existingRandom.isUsed() && !existingRandom.isExpired()) {
                throw new DomainException("请勿频繁发送");
            }
            existingRandom.refresh(expiresIn);
            send(existingRandom);
            randomRepository.updateById(existingRandom);
            return existingRandom;
        }
    }
    
    private void send(Random random) {
        if (random.getWay() == Random.Way.EMAIL) {
            randomMessageFacade.sendToEmail(EmailRandomMessage.from(random));
        } else if (random.getWay() == Random.Way.SMS) {
            randomMessageFacade.sendToSMS(SMSRandomMessage.from(random));
        } else {
            throw new DomainException("验证码类型异常");
        }
    }

}
