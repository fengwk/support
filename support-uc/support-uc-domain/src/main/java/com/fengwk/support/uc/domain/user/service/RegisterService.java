package com.fengwk.support.uc.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.exception.ExceptionCodes;
import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.util.ValidationUtils;
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
public class RegisterService {
 
    @Autowired
    volatile UserRepository userRepository;
    
    public User register(String email, String nickname, String password) {
        Preconditions.notEmpty(email, "邮箱不能为空");
        Preconditions.isTrue(ValidationUtils.isEmail(email), "邮箱格式错误");
        Preconditions.notEmpty(nickname, "昵称不能为空");
        Preconditions.notEmpty(password, "密码不能为空");
        checkExistsByEmail(email);
        User user = User.of(email, nickname, password);
        userRepository.add(user);
        return user;
    }
    
    private void checkExistsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("邮箱已被注册,email={}.", email);
            throw ExceptionCodes.biz().create("邮箱已被注册");
        }
    }
    
}
