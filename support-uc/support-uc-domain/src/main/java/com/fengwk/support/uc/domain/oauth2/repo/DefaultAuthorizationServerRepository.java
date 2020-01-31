package com.fengwk.support.uc.domain.oauth2.repo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;

import com.fengwk.support.uc.domain.oauth2.model.AuthorizationServer;

/**
 * 
 * @author fengwk
 */
public class DefaultAuthorizationServerRepository implements AuthorizationServerRepository {

    @Value("${uc.oauth2.baseUri}")
    volatile URI baseUri;
    
    @Override
    public AuthorizationServer any() {
        return new AuthorizationServer(baseUri);
    }

}
