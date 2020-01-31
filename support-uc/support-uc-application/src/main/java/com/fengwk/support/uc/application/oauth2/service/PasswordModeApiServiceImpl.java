package com.fengwk.support.uc.application.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.PasswordModeApiService;
import com.fengwk.support.uc.application.oauth2.converter.TokenConverter;
import com.fengwk.support.uc.domain.oauth2.model.PasswordTokenRequest;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.service.PasswordTokenService;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.service.UserService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Service
public class PasswordModeApiServiceImpl implements PasswordModeApiService {

    @Autowired
    volatile UserService userService;
    
    @Autowired
    volatile PasswordTokenService passwordTokenService;
    
    @Override
    public TokenDTO token(EmailAndPasswordTokenRequestDTO requestDTO) {
        User user = userService.checkPasswordAndGet(requestDTO.getEmail(), requestDTO.getPassword());
        PasswordTokenRequest request = convert(requestDTO, user.getId());
        Token token = passwordTokenService.token(request);
        return TokenConverter.convert(token);
    }
    
    private PasswordTokenRequest convert(EmailAndPasswordTokenRequestDTO requestDTO, long userId) {
        return new PasswordTokenRequest(
                requestDTO.getGrantType(), 
                requestDTO.getClientId(), 
                requestDTO.getClientSecret(), 
                userId);
    }

}
