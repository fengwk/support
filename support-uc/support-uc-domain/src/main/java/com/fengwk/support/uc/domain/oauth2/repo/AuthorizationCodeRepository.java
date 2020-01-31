package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;

/**
 * 
 * @author fengwk
 */
public interface AuthorizationCodeRepository {

    void add(AuthorizationCode authCode);
    
    void update(AuthorizationCode authCode);
    
    AuthorizationCode getByCode(String code);
    
}
