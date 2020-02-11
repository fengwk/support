package com.fengwk.support.uc.application.oauth2.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.oauth2.model.AuthorizationCodeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RefreshTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.AuthorizationCodeModeApiService;
import com.fengwk.support.uc.application.oauth2.converter.TokenConverter;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.RefreshTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.RefreshableToken;
import com.fengwk.support.uc.domain.oauth2.service.AuthorizationCodeAuthorizeService;
import com.fengwk.support.uc.domain.oauth2.service.AuthorizationCodeTokenService;
import com.fengwk.support.uc.domain.oauth2.service.RefreshTokenService;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;
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
public class AuthorizationCodeModeApiServiceImpl implements AuthorizationCodeModeApiService {

    @Autowired
    volatile AuthenticationService authenticationService;
    
    @Autowired
    volatile RandomCheckService randomCheckService;
    
    @Autowired
    volatile AuthorizationCodeAuthorizeService authorizationCodeAuthorizeService;
    
    @Autowired
    volatile AuthorizationCodeTokenService authorizationCodeTokenService;
    
    @Autowired
    volatile RefreshTokenService refreshTokenService;
    
    @Autowired
    volatile RandomRepository randomRepository;
    
    @Autowired
    volatile UserRepository userRepository;
    
    @Override
    public String authorize(EmailAndPasswordAuthRequestDTO requestDTO) {
        User user = authenticationService.authenticate(requestDTO.getEmail(), requestDTO.getPassword());
        AuthorizationCodeAuthRequest request = convert(requestDTO, user.getId());
        AuthorizationCode authCode = authorizationCodeAuthorizeService.authorize(request);
        return authCode.getCode();
    }

    @Override
    public String authorize(EmailAndRandomAuthRequestDTO requestDTO) {
        Random random = randomCheckService.checkValueWithUnusedAndGet(Random.Way.EMAIL, Random.Type.AUTH, requestDTO.getEmail(), requestDTO.getRandom());
        random.use();
        randomRepository.updateById(random);
        
        User user = new UserCheckedQuery(userRepository).getByEmailRequiredNonNull(requestDTO.getEmail());
        AuthorizationCodeAuthRequest request = convert(requestDTO, user.getId());
        AuthorizationCode authCode = authorizationCodeAuthorizeService.authorize(request);
        return authCode.getCode();
    }

    @Override
    public TokenDTO token(AuthorizationCodeTokenRequestDTO requestDTO) {
        AuthorizationCodeTokenRequest request = convert(requestDTO);
        RefreshableToken token = authorizationCodeTokenService.token(request);
        return TokenConverter.convert(token);
    }

    @Override
    public TokenDTO token(RefreshTokenRequestDTO requestDTO) {
        RefreshTokenRequest request = convert(requestDTO);
        RefreshableToken token = refreshTokenService.token(request);
        return TokenConverter.convert(token);
    }
    
    private AuthorizationCodeAuthRequest convert(EmailAndPasswordAuthRequestDTO requestDTO, long userId) {
        return new AuthorizationCodeAuthRequest(
                requestDTO.getResponseType(), 
                requestDTO.getClientId(), 
                URI.create(requestDTO.getRedirectUri()), 
                requestDTO.getScope(), 
                requestDTO.getState(), 
                userId);
    }
    
    private AuthorizationCodeAuthRequest convert(EmailAndRandomAuthRequestDTO requestDTO, long userId) {
        return new AuthorizationCodeAuthRequest(
                requestDTO.getResponseType(), 
                requestDTO.getClientId(), 
                URI.create(requestDTO.getRedirectUri()), 
                requestDTO.getScope(), 
                requestDTO.getState(), 
                userId);
    }
    
    private AuthorizationCodeTokenRequest convert(AuthorizationCodeTokenRequestDTO requestDTO) {
        return new AuthorizationCodeTokenRequest(
                requestDTO.getGrantType(), 
                requestDTO.getClientId(), 
                requestDTO.getClientSecret(), 
                requestDTO.getCode());
    }
    
    private RefreshTokenRequest convert(RefreshTokenRequestDTO requestDTO) {
        return new RefreshTokenRequest(
                requestDTO.getGrantType(), 
                requestDTO.getClientId(), 
                requestDTO.getClientSecret(), 
                requestDTO.getRefreshToken());
    }

}
