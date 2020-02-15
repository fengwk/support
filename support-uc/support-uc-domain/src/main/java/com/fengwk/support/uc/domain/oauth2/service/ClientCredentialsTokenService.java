package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.uc.domain.oauth2.OAuth2Constants;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.ClientCredentialsTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationCodeRepository;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class ClientCredentialsTokenService {

    @Autowired
    volatile TokenRecycleStrategy tokenRecycleStrategy;
    
    @Autowired
    volatile AuthorizationCodeRepository authorizationCodeRepository;
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    @Autowired
    volatile ClientRepository clientRepository;
    
    public Token token(ClientCredentialsTokenRequest request) {
        Preconditions.notNull(request, "令牌授权请求不能为空");
        Preconditions.isTrue(request.isClientCredentialsMode(), "令牌授权模式异常");
        
        Client client = new CheckedClientRepository(clientRepository).requiredNonNull().getById(request.getClientId());
        client.requiredEnable().requiredCorrectSecret(request.getClientSecret());
        
        tokenRecycleStrategy.recycle(request.getClientId(), null, client.isExclusive(), client.getTokenCountLimit());
        
        Token token = Token.create(request.getClientId(), null, OAuth2Constants.GRANT_TYPE_CLIENT_CREDENTIALS, null, client.getAccessExpiresIn());
        tokenRepository.add(token);
        return token;
    }
    
}
