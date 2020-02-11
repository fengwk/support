package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.domain.exception.DomainException;
import com.fengwk.support.uc.domain.oauth2.model.Client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@AllArgsConstructor
public class ClientCheckedQuery {

    final ClientRepository clientRepository;
    
    public Client getByIdRequiredAvailable(long id) {
        Client client = clientRepository.getById(id);
        if (client == null){
            log.warn("客户端不存在, clientId={}.", id);
            throw new DomainException("客户端不存在");
        }
        if (client.isDisabled()) {
            log.warn("客户端已禁用, clientId={}.", id);
            throw new DomainException("客户端已禁用");
        }
        return client;
    }
    
}
