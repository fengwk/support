package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Component
class ClientChecker {

    @Autowired
    volatile ClientRepository clientRepository;
    
    Client checkAndGet(long clientId) {
        Client client = clientRepository.get(clientId);
        if (client == null) {
            log.warn("客户端不存在,clientId={}.", clientId);
            throw ExceptionCodes.biz().create("客户端不存在");
        }
        if (client.isDisabled()) {
            log.warn("客户端已禁用,clientId={}.", clientId);
            throw ExceptionCodes.biz().create("客户端已禁用");
        }
        return client;
    }
    
}
