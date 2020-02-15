package com.fengwk.support.uc.application.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.convention.exception.Preconditions;
import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.page.PageQuery;
import com.fengwk.support.core.convention.query.Criteria;
import com.fengwk.support.core.convention.query.Query;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.uc.api.user.model.UserDTO;
import com.fengwk.support.uc.api.user.model.UserDescriptorDTO;
import com.fengwk.support.uc.api.user.model.UserSearchDTO;
import com.fengwk.support.uc.api.user.model.UserUpdateDTO;
import com.fengwk.support.uc.api.user.service.UserApiService;
import com.fengwk.support.uc.application.user.converter.UserConverter;
import com.fengwk.support.uc.domain.oauth2.model.Token;
import com.fengwk.support.uc.domain.oauth2.repo.TokenRepository;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.CheckedUserRepository;
import com.fengwk.support.uc.domain.user.repo.UserRepository;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class UserApiServiceImpl implements UserApiService {
    
    @Autowired
    volatile TokenRepository tokenRepository;
    
    @Autowired
    volatile UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO updateSelective(UserUpdateDTO updateDTO) {
        User user = new CheckedUserRepository(userRepository).requiredNonNull().getById(updateDTO.getId());
        user.update(updateDTO.getEmail(), updateDTO.getNickname(), true);
        userRepository.updateById(user);
        return UserConverter.convert(user);
    }

    @Override
    public Page<UserDTO> search(UserSearchDTO searchDTO) {
        Preconditions.isTrue(ValidationUtils.isLegalLike(searchDTO.getEmail()), "非法的邮箱");
        Preconditions.isTrue(ValidationUtils.isLegalLike(searchDTO.getNickname()), "非法的昵称");
        
        Query<User> query = new Query<>();
        Criteria<User> criteria = new Criteria<>();
        Optional<UserSearchDTO> opt = Optional.of(searchDTO);
        opt.map(UserSearchDTO::getEmail).ifPresent(email -> criteria.andLikePrefix(Property.of(User::getEmail), email));
        opt.map(UserSearchDTO::getNickname).ifPresent(nickname -> criteria.andLikePrefix(Property.of(User::getNickname), nickname));
        query.and(criteria);
        return userRepository.page(query, new PageQuery(searchDTO)).map(UserConverter::convert);
    }

    @Override
    public UserDescriptorDTO tryGetUserDescriptor(String accessToken) {
        Token token = tokenRepository.getByAccessToken(accessToken);
        if (token == null || token.isExpired() || token.isInvalid()) {
            return null;
        }
        User user = new CheckedUserRepository(userRepository).requiredNonNull().getById(token.getUserId());
        return convert(user);
    }
    
    private UserDescriptorDTO convert(User user) {
        if (user == null) {
            return null;
        }
        UserDescriptorDTO userDescriptorDTO = new UserDescriptorDTO();
        userDescriptorDTO.setUserId(user.getId());
        userDescriptorDTO.setEmail(user.getEmail());
        userDescriptorDTO.setNickname(user.getNickname());
        return userDescriptorDTO;
    }

}
