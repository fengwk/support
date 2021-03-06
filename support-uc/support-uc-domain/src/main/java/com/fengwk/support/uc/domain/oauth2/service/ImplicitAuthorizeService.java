package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.uc.domain.oauth2.OAuth2Constants;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.ImplicitAuthRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

/**
 * 隐藏模式模式授权服务
 * 
 * @author fengwk
 */
@Transactional
@Service
public class ImplicitAuthorizeService {

    @Autowired
    volatile TokenRecycleStrategy tokenRecycleStrategy;
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    @Autowired
    volatile ClientRepository clientRepository;
    
    public Token authorize(ImplicitAuthRequest request) {
        Preconditions.notNull(request, "认证授权请求不能为空");
        request.checkCompletable();
        Preconditions.isTrue(request.isImplicitMode(), "认证授权模式异常");
        
        Client client = new CheckedClientRepository(clientRepository).requiredNonNull().getById(request.getClientId());
        client.requiredEnable().requiredCheckedRedirectUri(request.getRedirectUri());
        
        tokenRecycleStrategy.recycle(request.getClientId(), request.getUserId(), client.isExclusive(), client.getTokenCountLimit());
        
        Token token = Token.create(request.getClientId(), request.getUserId(), OAuth2Constants.GRANT_TYPE_IMPLICIT, request.getScope(), client.getAccessExpiresIn());
        tokenRepository.add(token);
        return token;
    }
    
}
