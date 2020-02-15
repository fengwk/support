package com.fengwk.support.uc.domain.oauth2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

/**
 * 
 * @author fengwk
 */
@Component
class TokenRecycleStrategy {

    @Autowired
    volatile TokenRepository tokenRepository;
    
    void recycle(long clientId, Long resourceOwnerId, boolean isExclusive, int tokenCountLimit) {
        List<Token> oldTokens;
        if (resourceOwnerId == null) {
            oldTokens = tokenRepository.listValidWithIsNullUserId(clientId);
        } else {
            oldTokens = tokenRepository.listValid(clientId, resourceOwnerId);
        }
        
        invalidTokensIfNecessary(oldTokens, isExclusive, tokenCountLimit);
    }
    
    private void invalidTokensIfNecessary(List<Token> oldTokens, boolean isExclusive, int tokenCountLimit) {
        if (CollectionUtils.isEmpty(oldTokens)) {
            return;
        }
        
        if (isExclusive) {
            for (Token oldToken : oldTokens) {
                if (oldToken.isExpired()) {
                    // 失效超时的令牌
                    oldToken.invalid();
                    tokenRepository.updateByIdIfValid(oldToken);
                } else {
                    // 排它模式下,所有其余令牌都会被失效
                    oldToken.invalid();
                    tokenRepository.updateByIdIfValid(oldToken);
                }
            }
        } else {
            oldTokens = oldTokens.stream().sorted((t1, t2) -> t2.getCreatedTime().compareTo(t1.getCreatedTime())).collect(Collectors.toList());
            int conter = 0;
            for (Token oldToken : oldTokens) {
                if (oldToken.isExpired()) {
                    // 失效超时的令牌
                    oldToken.invalid();
                    tokenRepository.updateByIdIfValid(oldToken);
                } else if (conter == tokenCountLimit - 1) {
                    // 失效超过限制数的令牌
                    oldToken.invalid();
                    tokenRepository.updateByIdIfValid(oldToken);
                } else {
                    conter++;
                }
            }
        }
    }
    
}
