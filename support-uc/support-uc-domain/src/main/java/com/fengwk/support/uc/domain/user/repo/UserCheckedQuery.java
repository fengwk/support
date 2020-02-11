package com.fengwk.support.uc.domain.user.repo;

import com.fengwk.support.domain.exception.DomainException;
import com.fengwk.support.uc.domain.user.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@AllArgsConstructor
public class UserCheckedQuery {

    final UserRepository userRepository;
    
    public User getByIdRequiredNonNull(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            log.warn("用户不存在, userId={}.", id);
            throw new DomainException("用户不存在");
        }
        return user;
    }
    
    public User getByEmailRequiredNonNull(String email) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            log.warn("用户不存在, email={}.", email);
            throw new DomainException("用户不存在");
        }
        return user;
    }
    
}
