package com.fengwk.support.uc.domain.oauth2.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RevokeTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class RevokeTokenService {

    @Autowired
    volatile ClientChecker clientChecker;
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    public void revoke(RevokeTokenRequest request) {
        Token token = tokenRepository.getByAccessToken(request.getAccessToken());
        if (token == null || token.isInvalid() || token.isExpired()) {
            return;
        }
        
        checkIsSameClient(request, token);
        
        Client client = clientChecker.checkAndGet(request.getClientId());
        client.checkSecret(request.getClientSecret());
        
        token.invalid();
        tokenRepository.updateIfValid(token);
    }
    
    private void checkIsSameClient(RevokeTokenRequest request, Token token) {
        if (!Objects.equals(token.getClientId(), request.getClientId())) {
            log.warn("请求客户端不匹配,request={}", request);
            throw ExceptionCodes.biz().create("请求客户端不匹配");
        }
    }
    
}
