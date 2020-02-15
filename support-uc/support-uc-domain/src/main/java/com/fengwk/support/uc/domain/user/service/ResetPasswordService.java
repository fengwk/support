package com.fengwk.support.uc.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.CheckedUserRepository;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

/**
 * 
 * @author fengwk
 */
@Service
public class ResetPasswordService {

    @Autowired
    volatile EncryptionService encryptionService;
    
    @Autowired
    volatile UserRepository userRepository;
    
    public User reset(String email, String newCleartextPassword) {
        User user = new CheckedUserRepository(userRepository).requiredNonNull().getByEmail(email);
        String encryptedPassword = encryptionService.encryptPassword(newCleartextPassword);
        user.resetPassword(encryptedPassword);
        userRepository.updateById(user);
        return user;
    }
    
}
