package com.fengwk.support.uc.domain.oauth2.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.oauth2.model.Token;

/**
 * 
 * @author fengwk
 */
public class CheckedTokenRepository extends CheckedRepository<Token, TokenRepository> {

    public CheckedTokenRepository(TokenRepository repository) {
        super(repository);
    }

    public Token getByAccessToken(String accessToken) {
        Token token = repository().getByAccessToken(accessToken);
        checkAndThrowIfNecessary(token, () -> args(arg("accessToken", accessToken)));
        return token;
    }
    
    public CheckedTokenRepository requiredNonNull() {
        appendChecker(client -> client == null ? failure("令牌不存在") : success());
        return this;
    }
    
}
