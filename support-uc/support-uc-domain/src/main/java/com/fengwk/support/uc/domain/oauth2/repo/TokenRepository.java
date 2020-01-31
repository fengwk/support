package com.fengwk.support.uc.domain.oauth2.repo;

import java.util.List;

import com.fengwk.support.uc.domain.oauth2.model.Token;

/**
 * 
 * @author fengwk
 */
public interface TokenRepository {

    void add(Token token);
    
    void updateIfValid(Token token);
    
    Token getByAccessToken(String accessToken);
    
    Token getByRefreshToken(String refreshToken);
    
    List<Token> listValid(long clientId, long userId);
    
    List<Token> listValidWithIsNullUserId(long clientId);
    
}
