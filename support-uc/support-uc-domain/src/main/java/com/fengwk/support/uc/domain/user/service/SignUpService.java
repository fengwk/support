package com.fengwk.support.uc.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户注册服务
 * 
 * @author fengwk
 */
@Slf4j
@Component
public class SignUpService {
 
    @Autowired
    volatile EncryptionService encryptionService;
    
    @Autowired
    volatile UserRepository userRepository;
    
    public User signUp(String email, String nickname, String cleartextPassword) {
        if (userRepository.existsByEmail(email)) {
            log.warn("邮箱已被注册,email={}.", email);
            throw new DomainException("邮箱已被注册");
        }
        String encryptedPassword = encryptionService.encryptPassword(cleartextPassword);
        User user = User.create(email, nickname, encryptedPassword);
        userRepository.add(user);
        return user;
    }
    
}
