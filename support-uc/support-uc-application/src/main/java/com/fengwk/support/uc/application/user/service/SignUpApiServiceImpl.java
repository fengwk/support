package com.fengwk.support.uc.application.user.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomRegisterDTO;
import com.fengwk.support.uc.api.user.service.SignUpApiService;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCodeAuthRequest;
import com.fengwk.support.uc.domain.oauth2.service.AuthorizationCodeAuthorizeService;
import com.fengwk.support.uc.domain.security.model.Random;
import com.fengwk.support.uc.domain.security.repo.RandomRepository;
import com.fengwk.support.uc.domain.security.service.RandomSendService;
import com.fengwk.support.uc.domain.security.service.RandomCheckService;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.service.SignUpService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class SignUpApiServiceImpl implements SignUpApiService {
    
    final static int EXPIRES_EMAIL_RANDOM = 60 * 2;
    
    @Autowired
    volatile RandomSendService randomSendService;
    
	@Autowired
	volatile RandomCheckService randomCheckService;
	
	@Autowired
    volatile SignUpService registerService;
	
	@Autowired
	volatile AuthorizationCodeAuthorizeService authorizationCodeAuthorizeService;

	@Autowired
    volatile RandomRepository randomRepository;
    
	@Override
    public int sendEmailRandom(String email) {
        Random random = randomSendService.createAndSend(Random.Way.EMAIL, Random.Type.REGISTER, email, EXPIRES_EMAIL_RANDOM);
        return random.getExpiresIn();
    }
	
	@Override
    public boolean verifyEmailRandom(EmailAndRandomDTO emailAndRandomDTO) {
	    Random random = randomCheckService.checkValueWithUnusedAndGet(Random.Way.EMAIL, Random.Type.REGISTER, emailAndRandomDTO.getEmail(), emailAndRandomDTO.getRandom());
	    random.silence();
	    randomRepository.updateById(random);
        return true;
    }
	
    @Override
    public String signUpAndAuthorize(EmailAndRandomRegisterDTO registerDTO) {
        Random random = randomCheckService.checkValueWithSilencedAndGet(Random.Way.EMAIL, Random.Type.REGISTER, registerDTO.getEmail(), registerDTO.getRandom());
        random.use();
        randomRepository.updateById(random);
        
        User user = registerService.signUp(registerDTO.getEmail(), registerDTO.getNickname(), registerDTO.getPassword());
        AuthorizationCode authCode = authorizationCodeAuthorizeService.authorize(convert(registerDTO, user.getId()));
        return authCode.getCode();
    }
    
    private AuthorizationCodeAuthRequest convert(EmailAndRandomRegisterDTO registerDTO, long userId) {
        return new AuthorizationCodeAuthRequest(
                registerDTO.getResponseType(), 
                registerDTO.getClientId(), 
                URI.create(registerDTO.getRedirectUri()), 
                registerDTO.getScope(), 
                registerDTO.getState(), 
                userId);
    }

}
