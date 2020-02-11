package com.fengwk.support.uc.application.oauth2.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.ImplicitModeApiService;
import com.fengwk.support.uc.application.oauth2.converter.TokenConverter;
import com.fengwk.support.uc.domain.oauth2.model.ImplicitAuthRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.service.ImplicitAuthorizeService;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.service.RandomCheckService;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserCheckedQuery;
import com.fengwk.support.uc.domain.user.repo.UserRepository;
import com.fengwk.support.uc.domain.user.service.AuthenticationService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class ImplicitModeApiServiceImpl implements ImplicitModeApiService {

    @Autowired
    volatile AuthenticationService authenticationService;
    
    @Autowired
    volatile RandomCheckService randomCheckService;
    
    @Autowired
    volatile ImplicitAuthorizeService implicitAuthorizeService;
    
    @Autowired
    volatile UserRepository userRepository;
    
    @Override
    public TokenDTO authorize(EmailAndPasswordAuthRequestDTO requestDTO) {
        User user = authenticationService.authenticate(requestDTO.getEmail(), requestDTO.getPassword());
        ImplicitAuthRequest request = convert(requestDTO, user.getId());
        Token token = implicitAuthorizeService.authorize(request);
        return TokenConverter.convert(token);
    }

    @Override
    public TokenDTO authorize(EmailAndRandomAuthRequestDTO requestDTO) {
        randomCheckService.checkValueWithUnusedAndGet(
                Random.Way.EMAIL, 
                Random.Type.AUTH, 
                requestDTO.getEmail(), 
                requestDTO.getRandom());
        
        User user = new UserCheckedQuery(userRepository).getByEmailRequiredNonNull(requestDTO.getEmail());
        ImplicitAuthRequest request = convert(requestDTO, user.getId());
        Token token = implicitAuthorizeService.authorize(request);
        return TokenConverter.convert(token);
    }

    private ImplicitAuthRequest convert(EmailAndPasswordAuthRequestDTO requestDTO, long userId) {
        return new ImplicitAuthRequest(
                requestDTO.getResponseType(), 
                requestDTO.getClientId(), 
                URI.create(requestDTO.getRedirectUri()), 
                requestDTO.getScope(), 
                requestDTO.getState(), 
                userId);
    }
    
    private ImplicitAuthRequest convert(EmailAndRandomAuthRequestDTO requestDTO, long userId) {
        return new ImplicitAuthRequest(
                requestDTO.getResponseType(), 
                requestDTO.getClientId(), 
                URI.create(requestDTO.getRedirectUri()), 
                requestDTO.getScope(), 
                requestDTO.getState(), 
                userId);
    }
    
}
