package com.fengwk.support.uc.infrastructure.mysql.oauth2.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.uc.domain.oauth2.model.RefreshableToken;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.model.TokenDescriptor;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.TokenPO;

/**
 * 
 * @author fengwk
 */
@Component
public class TokenConverter implements UcConverter<Token, TokenPO> {

    @Override
    public Token convert(TokenPO tokenPO) {
        if (tokenPO == null) {
            return null;
        }
        
        Token token;
        if (isRefreshable(tokenPO)) {
            RefreshableToken rtoken = new RefreshableToken();
            rtoken.setRefreshToken(new TokenDescriptor(tokenPO.getRefreshToken(), tokenPO.getRefreshExpiresIn(), tokenPO.getRefreshCreatedTime()));
            token = rtoken;
        } else {
            token = new Token();
        }
        token.setAccessToken(new TokenDescriptor(tokenPO.getAccessToken(), tokenPO.getAccessExpiresIn(), tokenPO.getAccessCreatedTime()));

        token.setId(tokenPO.getId());
        token.setCreatedTime(tokenPO.getCreatedTime());
        token.setModifiedTime(tokenPO.getModifiedTime());
        
        token.setClientId(tokenPO.getClientId());
        token.setUserId(TokenUserIdConvertStrategy.fromPO(tokenPO.getUserId()));
        token.setGrantType(tokenPO.getGrantType());
        token.setScope(tokenPO.getScope());
        token.setTokenType(tokenPO.getTokenType());
        token.setInvalid(ConvertUtils.intToBool(tokenPO.getIsInvalid()));
        
        return token;
    }
    
    @Override
    public TokenPO convert(Token token) {
        if (token == null) {
            return null;
        }
        TokenPO tokenPO = new TokenPO();
        tokenPO.setId(token.getId());
        tokenPO.setCreatedTime(token.getCreatedTime());
        tokenPO.setModifiedTime(token.getModifiedTime());
        
        tokenPO.setClientId(token.getClientId());
        tokenPO.setUserId(TokenUserIdConvertStrategy.toPO(token.getUserId()));
        tokenPO.setGrantType(token.getGrantType());
        tokenPO.setScope(token.getScope());
        tokenPO.setTokenType(token.getTokenType());
        
        tokenPO.setAccessToken(ConvertUtils.mapIfNotNull(token.getAccessToken(), TokenDescriptor::getToken));
        tokenPO.setAccessExpiresIn(ConvertUtils.mapIfNotNull(token.getAccessToken(), TokenDescriptor::getExpiresIn));
        tokenPO.setAccessCreatedTime(ConvertUtils.mapIfNotNull(token.getAccessToken(), TokenDescriptor::getCreatedTime));

        if (token.isRefreshable()) {
            RefreshableToken rtoken = (RefreshableToken) token;
            tokenPO.setRefreshToken(ConvertUtils.mapIfNotNull(rtoken.getRefreshToken(), TokenDescriptor::getToken));
            tokenPO.setRefreshExpiresIn(ConvertUtils.mapIfNotNull(rtoken.getRefreshToken(), TokenDescriptor::getExpiresIn));
            tokenPO.setRefreshCreatedTime(ConvertUtils.mapIfNotNull(rtoken.getRefreshToken(), TokenDescriptor::getCreatedTime));
        }
        
        tokenPO.setIsInvalid(ConvertUtils.boolToInt(token.isInvalid()));
        
        return tokenPO;
    }
    
    private static boolean isRefreshable(TokenPO tokenPO) {
        return StringUtils.isNotBlank(tokenPO.getRefreshToken()) && tokenPO.getRefreshExpiresIn() != null && tokenPO.getRefreshCreatedTime() != null;
    }
    
}
