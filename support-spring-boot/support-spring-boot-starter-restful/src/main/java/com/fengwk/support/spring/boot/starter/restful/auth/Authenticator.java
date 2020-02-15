package com.fengwk.support.spring.boot.starter.restful.auth;

/**
 * 
 * @author fengwk
 */
public interface Authenticator {

    /**
     * 成功返回认证信息,否则返回null
     * 
     * @param accessToken
     * @return
     */
    AuthorizationInfo authorize(String accessToken);
    
}
