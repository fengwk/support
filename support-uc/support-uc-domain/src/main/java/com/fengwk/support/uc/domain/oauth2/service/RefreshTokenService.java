package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.uc.domain.oauth2.model.Client;
import com.fengwk.support.uc.domain.oauth2.model.RefreshTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.RefreshableToken;
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
public class RefreshTokenService {

    @Autowired
    volatile ClientChecker clientChecker;
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    public RefreshableToken token(RefreshTokenRequest request) {
        Preconditions.notNull(request, "令牌授权请求不能为空");
        Preconditions.isTrue(request.isRefreshToken(), "令牌授权模式异常");
        
        Client client = clientChecker.checkAndGet(request.getClientId());
        client.checkSecret(request.getClientSecret());
        
        RefreshableToken token = checkoutToken(request.getRefreshToken());
        
        token.refresh(client.getAccessExpiresIn());
        tokenRepository.updateIfValid(token);
        return token;
    }
    
    private RefreshableToken checkoutToken(String refreshToken) {
        Token token = tokenRepository.getByRefreshToken(refreshToken);
        if (token == null) {
            log.warn("刷新令牌不存在,refreshToken={}.", refreshToken);
            throw ExceptionCodes.biz().create("刷新令牌不存在");
        }
        if (!token.isRefreshable()) {
            log.warn("刷新令牌无效,refreshToken={}.", refreshToken);
            throw ExceptionCodes.biz().create("刷新令牌无效");
        }
        RefreshableToken rtoken = (RefreshableToken) token;
        if (rtoken.getRefreshToken().isExpired()) {
            log.warn("刷新令牌已过期,refreshToken={}.", refreshToken);
            throw ExceptionCodes.biz().create("刷新令牌已过期");
        }
        return rtoken;
    }
     
}
