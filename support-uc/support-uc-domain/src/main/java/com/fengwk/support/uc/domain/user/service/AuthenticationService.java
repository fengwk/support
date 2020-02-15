package com.fengwk.support.uc.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 身份验证服务
 * 
 * @author fengwk
 */
@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    volatile UserRepository userRepository;
    
    @Autowired
    volatile EncryptionService encryptionService;
    
    /**
     * 认证并返回用户描述信息
     * 
     * @param email
     * @param cleartextPassword
     * @return
     * @throws DomainException 用户不存在或认证失败时抛出
     */
    public User authenticate(String email, String cleartextPassword) {
        Preconditions.notNull(email, "邮箱不能为空");
        Preconditions.notBlank(cleartextPassword, "明文密码不能为空");
        
        User user = userRepository.getByEmail(email);
        if (user == null) {
            log.warn("用户不存在, email={}.", email);
            throw new DomainException("用户不存在");
        }
        
        String encryptedPassword = encryptionService.encryptPassword(cleartextPassword);
        user.requiredCorrectPassword(encryptedPassword);
        return user;
    }
    
}
