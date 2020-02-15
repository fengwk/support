package com.fengwk.support.uc.domain.user.repo;

import com.fengwk.support.core.domain.repo.CheckedRepository;
import com.fengwk.support.uc.domain.user.model.User;

/**
 * 
 * @author fengwk
 */
public class CheckedUserRepository extends CheckedRepository<User, UserRepository> {

    public CheckedUserRepository(UserRepository repository) {
        super(repository);
    }

    public User getById(long id) {
        User user = repository().getById(id);
        checkAndThrowIfNecessary(user, () -> args(arg("userId", id)));
        return user;
    }
    
    public User getByEmail(String email) {
        User user = repository().getByEmail(email);
        checkAndThrowIfNecessary(user, () -> args(arg("email", email)));
        return user;
    }
    
    public CheckedUserRepository requiredNonNull() {
        appendChecker(user -> user == null ? failure("用户不存在") : success());
        return this;
    }
    
}
