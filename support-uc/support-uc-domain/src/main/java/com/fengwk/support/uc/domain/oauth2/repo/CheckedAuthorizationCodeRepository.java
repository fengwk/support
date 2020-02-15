package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.oauth2.model.AuthorizationCode;

/**
 * 
 * @author fengwk
 */
public class CheckedAuthorizationCodeRepository extends CheckedRepository<AuthorizationCode, AuthorizationCodeRepository> {

    public CheckedAuthorizationCodeRepository(AuthorizationCodeRepository repository) {
        super(repository);
    }
    
    public AuthorizationCode getByCode(String code) {
        AuthorizationCode authCode = repository().getByCode(code);
        checkAndThrowIfNecessary(authCode, () -> args(arg("code", code)));
        return authCode;
    }
    
    public CheckedAuthorizationCodeRepository requiredNonNull() {
        appendChecker(authCode -> authCode == null ? failure("授权码不存在") : success());
        return this;
    }

}
