package com.fengwk.support.uc.domain.oauth2.model;

import java.time.LocalDateTime;
import com.fengwk.support.core.domain.model.ValueObject;
import com.fengwk.support.core.util.DateUtils;
import com.fengwk.support.core.util.UuidUtils;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class TokenDescriptor implements ValueObject {

    /**
     * 令牌值
     */
    final String token;
    
    /**
     * 令牌超时时间
     */
    final int expiresIn;
    
    /**
     * 令牌创建时间
     */
    final LocalDateTime createdTime;

    public static TokenDescriptor of(int expiresIn, LocalDateTime createdTime) {
        return new TokenDescriptor(UuidUtils.genShort(), expiresIn, createdTime);
    }
    
    public TokenDescriptor(String token, int expiresIn, LocalDateTime createdTime) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.createdTime = createdTime;
    }

    /**
     * 令牌到期时间
     * 
     * @return unix ms
     */
    public long expiresTime() {
        return DateUtils.toUnixMs(createdTime) + expiresIn * 1000;
    }
    
    /**
     * 判断令牌是否过期
     * 
     * @return
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresTime();
    }

}
