package com.fengwk.support.uc.application.oauth2.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.oauth2.model.AuthorizationServerRedirectRequestDTO;
import com.fengwk.support.uc.api.oauth2.service.AuthorizationCodeModeApiService;
import com.fengwk.support.uc.api.oauth2.service.ClientCredentialsModeApiService;
import com.fengwk.support.uc.api.oauth2.service.ImplicitModeApiService;
import com.fengwk.support.uc.api.oauth2.service.OAuth2ApiService;
import com.fengwk.support.uc.api.oauth2.service.PasswordModeApiService;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationServer;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationServerRedirectRequest;
import com.fengwk.support.uc.domain.oauth2.repo.AuthorizationServerRepository;
import com.fengwk.support.uc.domain.oauth2.service.RevokeTokenService;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.service.RandomSendService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class OAuth2ApiServiceImpl implements OAuth2ApiService {

    final static int EXPIRES_EMAIL_RANDOM = 60 * 2;
    
    @Autowired
    volatile RandomSendService randomSendService;
    
    @Autowired
    volatile AuthorizationCodeModeApiService authorizationCodeModeApiService;
    
    @Autowired
    volatile ImplicitModeApiService implicitModeApiService;
    
    @Autowired
    volatile PasswordModeApiService passwordModeApiService;
    
    @Autowired
    volatile ClientCredentialsModeApiService clientCredentialsModeApiService;
    
    @Autowired
    volatile RevokeTokenService revokeTokenService;
    
    @Autowired
    volatile AuthorizationServerRepository authorizationServerRepository;
    
    @Override
    public int sendEmailRandom(String email) {
        Random random = randomSendService.createAndSend(Random.Way.EMAIL, Random.Type.AUTH, email, EXPIRES_EMAIL_RANDOM);
        return random.getExpiresIn();
    }
    
    @Override
    public String redirectUri(AuthorizationServerRedirectRequestDTO requestDTO) {
        AuthorizationServer server = authorizationServerRepository.any();
        URI redirectUri = server.redirectUri(convert(requestDTO));
        return redirectUri.toASCIIString();
    }

    @Override
    public void revoke(String accessToken) {
        revokeTokenService.revoke(accessToken);
    }

    @Override
    public AuthorizationCodeModeApiService authorizationCodeMode() {
        return authorizationCodeModeApiService;
    }

    @Override
    public ImplicitModeApiService implicitMode() {
        return implicitModeApiService;
    }

    @Override
    public PasswordModeApiService passwordMode() {
        return passwordModeApiService;
    }

    @Override
    public ClientCredentialsModeApiService clientCredentialsMode() {
        return clientCredentialsModeApiService;
    }
    
    private AuthorizationServerRedirectRequest convert(AuthorizationServerRedirectRequestDTO requestDTO) {
        AuthorizationServerRedirectRequest request = new AuthorizationServerRedirectRequest(
                requestDTO.getResponseType(), 
                requestDTO.getClientId(), 
                URI.create(requestDTO.getRedirectUri()), 
                requestDTO.getScope(), 
                requestDTO.getState());
        return request;
    }

}
