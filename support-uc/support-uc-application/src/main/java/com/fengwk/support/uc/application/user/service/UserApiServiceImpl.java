package com.fengwk.support.uc.application.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.user.service.UserApiService;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class UserApiServiceImpl implements UserApiService {
    
    @Autowired
    volatile UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
}
