package com.fengwk.support.uc.application.oauth2.converter;

import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.domain.oauth2.model.RefreshableToken;
import com.fengwk.support.uc.domain.oauth2.model.Token;

/**
 * 
 * @author fengwk
 */
public class TokenConverter {

    private TokenConverter() {}
    
    public static TokenDTO convert(Token token) {
        if (token == null) {
            return null;
        }
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setTokenType(token.getTokenType());
        tokenDTO.setExpiresIn(token.getAccessToken().getExpiresIn());
        tokenDTO.setScope(token.getScope());
        tokenDTO.setAccessToken(token.getAccessToken().getToken());
        if (token.isRefreshable()) {
            RefreshableToken rtoken = (RefreshableToken) token;
            tokenDTO.setRefreshToken(rtoken.getRefreshToken().getToken());
        }
        return tokenDTO;
    }
    
}
