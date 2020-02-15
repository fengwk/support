package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;

import com.fengwk.support.core.domain.exception.DomainException;
import com.fengwk.support.core.util.DateUtils;
import com.fengwk.support.core.util.UuidUtils;
import com.fengwk.support.uc.domain.UcEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 授权码
 * 
 * @author fengwk
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationCode extends UcEntity {
    
    /**
     * 授权码
     */
    String code;
    
    /**
     * 授权码超时时间/秒
     */
    int expiresIn;
    
    /**
     * 是否使用过
     */
    boolean isUsed;
    
    /**
     * 授权认证请求
     */
    AuthorizationCodeAuthRequest boundRequest;
    
    public static AuthorizationCode create(int expiresIn, AuthorizationCodeAuthRequest boundRequest) {
        AuthorizationCode authCode = new AuthorizationCode();
        authCode.code = UuidUtils.genShort();
        authCode.expiresIn = expiresIn;
        authCode.isUsed = false;
        authCode.boundRequest = boundRequest;
        return authCode;
    }
    
    public AuthorizationCode requiredUnused() {
        if (isUsed()) {
            log.warn("授权码已被使用, authCode={}.", this);
            throw new DomainException("授权码已被使用");
        }
        return this;
    }
    
    public AuthorizationCode requiredUnexpired() {
        if (isExpired()) {
            log.warn("授权码已过期, authCode={}.", this);
            throw new DomainException("授权码已过期");
        }
        return this;
    }
    
    /**
     * 使用该授权码
     */
    public void use() {
        isUsed = true;
    }
    
    /**
     * 授权码超时时间,unix ms
     * 
     * @return
     */
    public long expiresTime() {
        return DateUtils.toUnixMs(createdTime) + expiresIn * 1000;
    }
    
    /**
     * 检查授权码是否已超时
     * 
     * @return
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresTime();
    }
    
    /**
     * 组成重定向地址
     * 
     * @return
     */
    public URI redirectUri() {
        try {
            URIBuilder builder = new URIBuilder(boundRequest.getRedirectUri());
            builder.addParameter("code", code);
            boundRequest.appendState(builder);
            return builder.build();
        } catch (URISyntaxException e) {
            log.error("", e);
            throw new DomainException(e);
        }
    }
    
}
