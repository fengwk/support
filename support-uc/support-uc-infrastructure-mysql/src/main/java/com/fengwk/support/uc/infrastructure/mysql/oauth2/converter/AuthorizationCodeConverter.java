package com.fengwk.support.uc.infrastructure.mysql.oauth2.converter;

import java.net.URI;

import org.springframework.stereotype.Component;

import com.fengwk.support.core.util.ConvertUtils;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.infrastructure.mysql.UcConverter;
import com.fengwk.support.uc.infrastructure.mysql.oauth2.model.AuthorizationCodePO;

/**
 * 
 * @author fengwk
 */
@Component
public class AuthorizationCodeConverter implements UcConverter<AuthorizationCode, AuthorizationCodePO> {

    @Override
    public AuthorizationCode convert(AuthorizationCodePO authorizationCodePO) {
        if (authorizationCodePO == null) {
            return null;
        }
        AuthorizationCode authorizationCode = new AuthorizationCode();
        
        authorizationCode.setId(authorizationCodePO.getId());
        authorizationCode.setCreatedTime(authorizationCodePO.getCreatedTime());
        authorizationCode.setModifiedTime(authorizationCodePO.getModifiedTime());
        
        authorizationCode.setCode(authorizationCodePO.getCode());
        authorizationCode.setExpiresIn(authorizationCodePO.getExpiresIn());
        authorizationCode.setUsed(ConvertUtils.intToBool(authorizationCodePO.getIsUsed()));
        
        AuthorizationCodeAuthRequest request = new AuthorizationCodeAuthRequest(
                authorizationCodePO.getResponseType(),
                authorizationCodePO.getClientId(),
                URI.create(authorizationCodePO.getRedirectUri()),
                authorizationCodePO.getScope(),
                authorizationCodePO.getState(),
                authorizationCodePO.getUserId());
        authorizationCode.setBoundRequest(request);
        
        return authorizationCode;
    }
    
    @Override
    public AuthorizationCodePO convert(AuthorizationCode authorizationCode) {
        if (authorizationCode == null) {
            return null;
        }
        AuthorizationCodePO authorizationCodePO = new AuthorizationCodePO();
        
        authorizationCodePO.setId(authorizationCode.getId());
        authorizationCodePO.setCreatedTime(authorizationCode.getCreatedTime());
        authorizationCodePO.setModifiedTime(authorizationCode.getModifiedTime());
        
        authorizationCodePO.setCode(authorizationCode.getCode());
        authorizationCodePO.setExpiresIn(authorizationCode.getExpiresIn());
        authorizationCodePO.setIsUsed(ConvertUtils.boolToInt(authorizationCode.isUsed()));
        
        AuthorizationCodeAuthRequest request = authorizationCode.getBoundRequest();
        if (request != null) {
            authorizationCodePO.setResponseType(request.getResponseType());
            authorizationCodePO.setClientId(request.getClientId());
            authorizationCodePO.setRedirectUri(request.getRedirectUri().toString());
            authorizationCodePO.setScope(request.getScope());
            authorizationCodePO.setState(request.getState());
            authorizationCodePO.setUserId(request.getUserId());
        }
        
        return authorizationCodePO;
    }
    
}
