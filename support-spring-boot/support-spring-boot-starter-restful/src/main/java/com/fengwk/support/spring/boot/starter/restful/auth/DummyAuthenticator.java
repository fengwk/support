package com.fengwk.support.spring.boot.starter.restful.auth;

/**
 * 
 * @author fengwk
 */
public class DummyAuthenticator implements Authenticator {

    @Override
    public AuthorizationInfo authorize(String accessToken) {
        return null;
    }

}
