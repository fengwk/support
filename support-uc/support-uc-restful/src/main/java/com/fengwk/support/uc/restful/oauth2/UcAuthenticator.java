package com.fengwk.support.uc.restful.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.spring.boot.starter.restful.auth.Authenticator;
import com.fengwk.support.spring.boot.starter.restful.auth.AuthorizationInfo;
import com.fengwk.support.uc.api.user.model.UserDescriptorDTO;
import com.fengwk.support.uc.api.user.service.UserApiService;

/**
 * 
 * @author fengwk
 */
@Component
public class UcAuthenticator implements Authenticator {

    @Autowired
    volatile UserApiService userApiService;
    
    @Override
    public AuthorizationInfo authorize(String accessToken) {
        UserDescriptorDTO userDescriptorDTO = userApiService.tryGetUserDescriptor(accessToken);
        if (userDescriptorDTO == null) {
            return null;
        }
        return new AuthorizationInfo(accessToken, userDescriptorDTO.getUserId());
    }

}
