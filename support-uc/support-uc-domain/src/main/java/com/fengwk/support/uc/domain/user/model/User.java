package com.fengwk.support.uc.domain.user.model;

import java.util.Objects;

import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends UcEntity {
    
    String email;
    String nickname;
    String password;
    
    public static User of(String email, String nickname, String encryptedPassword) {
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        user.password = encryptedPassword;
        return user;
    }
    
    public boolean isCorrectPassword(String encryptedPassword) {
        return Objects.equals(encryptedPassword, this.password);
    }
    
    public void resetPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
    
    public void updateSelective(String email, String nickname) {
        
    }
    
    public void checkAndSetEmail(String email) {
        Preconditions.notEmpty(email, "邮箱不能为空");
        Preconditions.isTrue(ValidationUtils.isEmail(email), "邮箱格式错误");
        this.email = email;
    }
    
    public void checkAndSetNickname(String nickname) {
        Preconditions.notEmpty(nickname, "昵称不能为空");
        this.nickname = nickname;
    }
    
}
