package com.fengwk.support.uc.application.user.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomResetPasswordResetDTO;
import com.fengwk.support.uc.api.user.service.ResetApiService;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.domain.oauth2.service.AuthorizationCodeAuthorizeService;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;
import com.fengwk.support.uc.domain.security.service.RandomSendService;
import com.fengwk.support.uc.domain.security.service.RandomService;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;
import com.fengwk.support.uc.domain.user.service.UserService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class ResetApiServiceImpl implements ResetApiService {
    
    final static int EXPIRES_EMAIL_RANDOM = 60 * 2;
    
    @Autowired
    volatile RandomSendService randomSendService;
    
    @Autowired
    volatile RandomService randomService;
    
    @Autowired
    volatile UserService userService;
    
    @Autowired
    volatile AuthorizationCodeAuthorizeService authorizationCodeAuthorizeService;

    @Autowired
    volatile RandomRepository randomRepository;
    
    @Autowired
    volatile UserRepository userRepository;
    
    @Override
    public int sendEmailRandom(String email) {
        Random random = randomSendService.createAndSend(Random.Way.EMAIL, Random.Type.RESET, email, EXPIRES_EMAIL_RANDOM);
        return random.getExpiresIn();
    }

    @Override
    public boolean verifyEmailRandom(EmailAndRandomDTO emailAndRandomDTO) {
        Random random = randomService.checkValueWithIsUnusedAndGet(Random.Way.EMAIL, Random.Type.RESET, emailAndRandomDTO.getEmail(), emailAndRandomDTO.getRandom());
        random.silence();
        randomRepository.update(random);
        return true;
    }

    @Override
    public String resetPasswordAndAuthorize(EmailAndRandomResetPasswordResetDTO resetDTO) {
        Random random = randomService.checkValueWithIsSilencedAndGet(Random.Way.EMAIL, Random.Type.REGISTER, resetDTO.getEmail(), resetDTO.getRandom());
        random.use();
        randomRepository.update(random);
        
        User user = userService.checkout(resetDTO.getEmail());
        user.resetPassword(resetDTO.getNewPassword());
        userRepository.update(user);
        
        AuthorizationCode authCode = authorizationCodeAuthorizeService.authorize(convert(resetDTO, user.getId()));
        return authCode.getCode();
    }

    private AuthorizationCodeAuthRequest convert(EmailAndRandomResetPasswordResetDTO resetDTO, long userId) {
        return new AuthorizationCodeAuthRequest(
                resetDTO.getResponseType(), 
                resetDTO.getClientId(), 
                URI.create(resetDTO.getRedirectUri()), 
                resetDTO.getScope(), 
                resetDTO.getState(), 
                userId);
    }
    
}
