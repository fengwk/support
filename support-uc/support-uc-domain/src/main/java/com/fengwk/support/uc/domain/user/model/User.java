package com.fengwk.support.uc.domain.user.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends UcEntity {
    
    String email;
    String nickname;
    String password;
    
    public static User create(String email, String nickname, String encryptedPassword) {
        checkEmail(email);
        checkNickname(nickname);
        checkEncryptedPassword(encryptedPassword);
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        user.password = encryptedPassword;
        return user;
    }
    
    public void requiredCorrectPassword(String encryptedPassword) {
        if (!isCorrectPassword(encryptedPassword)) {
            log.warn("密码错误, user={}, cleartextPassword={}.", this, encryptedPassword);
            throw new DomainException("密码错误");
        }
    }
    
    private boolean isCorrectPassword(String encryptedPassword) {
        return Objects.equals(encryptedPassword, this.password);
    }
    
    public void resetPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    
    public void update(String email, String nickname, boolean isSelective) {
        if (!isSelective || StringUtils.isNotBlank(email)) {
            checkEmail(email);
            this.email = email;
        }
        if (!isSelective || StringUtils.isNotBlank(nickname)) {
            checkNickname(nickname);;
            this.nickname = nickname;
        }
    }
    
    private static void checkEmail(String email) {
        Preconditions.notEmpty(email, "邮箱不能为空");
        Preconditions.isTrue(ValidationUtils.isEmail(email), "邮箱格式错误");
        Preconditions.isTrue(email.length() <= 64, "邮箱不能超过64位");
    }
    
    private static void checkNickname(String nickname) {
        Preconditions.notEmpty(nickname, "昵称不能为空");
        Preconditions.isTrue(nickname.length() <= 64, "昵称不能超过64位");
    }
    
    private static void checkEncryptedPassword(String encryptedPassword) {
        Preconditions.notEmpty(encryptedPassword, "加密密码不能为空");
        Preconditions.isTrue(encryptedPassword.length() <= 32, "加密密码不能超过32位");
    }
    
}
