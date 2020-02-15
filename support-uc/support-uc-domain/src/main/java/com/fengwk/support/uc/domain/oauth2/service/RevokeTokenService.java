package com.fengwk.support.uc.domain.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.CheckedTokenRepository;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;

/**
 * 
 * @author fengwk
 */
@Transactional
@Service
public class RevokeTokenService {

    @Autowired
    volatile TokenRepository tokenRepository;
    
    public void revoke(String accessToken) {
        Token token = new CheckedTokenRepository(tokenRepository).requiredNonNull().getByAccessToken(accessToken);
        
        if (token.isExpired() || token.isInvalid()) {
            return;
        }
        
        token.invalid();
        tokenRepository.updateByIdIfValid(token);
    }
    
}
