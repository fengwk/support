package com.fengwk.support.uc.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Transactional
@Service
public class UserService {

    @Autowired
    volatile UserRepository userRepository;
    
    public User checkout(String email) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            log.warn("用户不存在,email={}.", email);
            throw ExceptionCodes.biz().create("用户不存在");
        }
        return user;
    }
    
    public User checkPasswordAndGet(String email, String password) {
        User user = checkout(email);
        if (!user.verifyPassword(password)) {
            log.warn("密码错误,email={},password={}.", email, password);
            throw ExceptionCodes.biz().create("密码错误");
        }
        return user;
    }
    
}
