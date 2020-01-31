package com.fengwk.support.uc.domain.user.model;

import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

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
    
    public static User of(String email, String nickname, String cleartextPassword) {
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        user.password = digest(cleartextPassword);
        return user;
    }
    
    static String digest(String cleartextPassword) {
        if (StringUtils.isBlank(cleartextPassword)) {
            return null;
        }
        return DigestUtils.md5Hex(cleartextPassword);
    }
    
    public void resetPassword(String cleartextPassword) {
        this.password = digest(cleartextPassword);
    }
    
    public boolean verifyPassword(String cleartextPassword) {
        if (this.password == null || cleartextPassword == null) {
            return false;
        }
        return Objects.equals(this.password, digest(cleartextPassword));
    }
    
}
