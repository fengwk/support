package com.fengwk.support.uc.application.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.oauth2.model.ClientCredentialsTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.ClientCredentialsModeApiService;
import com.fengwk.support.uc.application.oauth2.converter.TokenConverter;
import com.fengwk.support.uc.domain.oauth2.model.ClientCredentialsTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.service.ClientCredentialsTokenService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class ClientCredentialsModeApiServiceImpl implements ClientCredentialsModeApiService {
    
    @Autowired
    volatile ClientCredentialsTokenService clientCredentialsTokenService;
    
    @Override
    public TokenDTO token(ClientCredentialsTokenRequestDTO requestDTO) {
        ClientCredentialsTokenRequest request = convert(requestDTO);
        Token token = clientCredentialsTokenService.token(request);
        return TokenConverter.convert(token);
    }
    
    private ClientCredentialsTokenRequest convert(ClientCredentialsTokenRequestDTO requestDTO) {
        return new ClientCredentialsTokenRequest(
                requestDTO.getGrantType(), 
                requestDTO.getClientId(), 
                requestDTO.getClientSecret());
    }

}
