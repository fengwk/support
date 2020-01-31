package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationCodeRepository;

/**
 * 授权码模式授权服务
 * 
 * @author fengwk
 */
@Transactional
@Service
public class AuthorizationCodeAuthorizeService {

    static final int DEFAULT_AUTHORIZATION_CODE_EXPIRES_IN = 60 * 10;// 采用默认的授权码超时时间10分钟
    
    @Autowired
    volatile ClientChecker clientChecker;
    
    @Autowired
    volatile AuthorizationCodeRepository authorizationCodeRepository;
    
    public AuthorizationCode authorize(AuthorizationCodeAuthRequest request) {
        Preconditions.notNull(request, "认证授权请求不能为空");
        request.checkCompletable();
        Preconditions.isTrue(request.isAuthorizationCodeMode(), "认证授权模式异常");
        
        Client client = clientChecker.checkAndGet(request.getClientId());
        client.checkRedirectUri(request.getRedirectUri());
        
        AuthorizationCode authCode = AuthorizationCode.of(DEFAULT_AUTHORIZATION_CODE_EXPIRES_IN, request);
        authorizationCodeRepository.add(authCode);
        return authCode;
    }
    
}
