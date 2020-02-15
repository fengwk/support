package com.fengwk.support.spring.boot.starter.restful.auth;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class AuthorizationInfo {

    private final String accessToken;
    private final long userId;
    
}
