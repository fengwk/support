package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.uc.domain.oauth2.OAuth2Constants;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RefreshableToken;
import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationCodeRepository;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedAuthorizationCodeRepository;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.ClientRepository;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class AuthorizationCodeTokenService {

    @Autowired
    volatile TokenRecycleStrategy tokenRecycleStrategy;
    
    @Autowired
    volatile AuthorizationCodeRepository authorizationCodeRepository;
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    @Autowired
    volatile ClientRepository clientRepository;
    
    public RefreshableToken token(AuthorizationCodeTokenRequest request) {
        Preconditions.notNull(request, "令牌授权请求不能为空");
        Preconditions.isTrue(request.isAuthorizationCodeMode(), "令牌授权模式异常");
        
        AuthorizationCode authCode = new CheckedAuthorizationCodeRepository(authorizationCodeRepository).requiredNonNull().getByCode(request.getCode());
        authCode.requiredUnused().requiredUnexpired().use();
        authorizationCodeRepository.updateById(authCode);
        
        AuthorizationCodeAuthRequest boundRequest = authCode.getBoundRequest();
        checkIsSameClient(request, boundRequest);
        
        Client client = new CheckedClientRepository(clientRepository).requiredNonNull().getById(request.getClientId());
        client.requiredEnable().requiredCorrectSecret(request.getClientSecret());
        
        tokenRecycleStrategy.recycle(boundRequest.getClientId(), boundRequest.getUserId(), client.isExclusive(), client.getTokenCountLimit());
        
        RefreshableToken token = RefreshableToken.create(boundRequest.getClientId(), boundRequest.getUserId(), OAuth2Constants.GRANT_TYPE_AUTHORIZATION_CODE, boundRequest.getScope(), client.getAccessExpiresIn(), client.getRefreshExpiresIn());
        tokenRepository.add(token);
        return token;
    }
    
    private void checkIsSameClient(AuthorizationCodeTokenRequest request, AuthorizationCodeAuthRequest boundRequest) {
        if (request.getClientId() != boundRequest.getClientId()) {
            log.warn("请求客户端id与授权码绑定的客户端id不一致,request={}.", request);
            throw new DomainException("请求客户端id与授权码绑定的客户端id不一致");
        }
    }
    
}
