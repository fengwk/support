package com.fengwk.support.uc.application.user.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.core.bean.Property;
import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQuery;
import com.fengwk.support.core.query.Criteria;
import com.fengwk.support.core.query.Query;
import com.fengwk.support.core.util.ValidationUtils;
import com.fengwk.support.uc.api.user.model.UserEntityDTO;
import com.fengwk.support.uc.api.user.model.UserQueryDTO;
import com.fengwk.support.uc.api.user.model.UserUpdateDTO;
import com.fengwk.support.uc.api.user.service.UserApiService;
import com.fengwk.support.uc.application.user.converter.UserEntityConverter;
import com.fengwk.support.uc.domain.user.model.User;
import com.fengwk.support.uc.domain.user.repo.UserCheckedQuery;
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
    volatile UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntityDTO updateSelective(UserUpdateDTO updateDTO) {
        User user = new UserCheckedQuery(userRepository).getByIdRequiredNonNull(updateDTO.getId());
        if (StringUtils.isNotBlank(updateDTO.getEmail())) {
            user.checkAndSetEmail(updateDTO.getEmail());
        }
        if (StringUtils.isNotBlank(updateDTO.getNickname())) {
            user.checkAndSetNickname(updateDTO.getNickname());
        }
        userRepository.updateById(user);
        return UserEntityConverter.convert(user);
    }

    @Override
    public Page<UserEntityDTO> query(UserQueryDTO queryDTO) {
        Preconditions.isTrue(ValidationUtils.isLegalLike(queryDTO.getEmail()), "非法的邮箱");
        Preconditions.isTrue(ValidationUtils.isLegalLike(queryDTO.getNickname()), "非法的昵称");
        
        Query<User> query = new Query<>();
        Criteria<User> criteria = new Criteria<>();
        Optional<UserQueryDTO> opt = Optional.of(queryDTO);
        opt.map(UserQueryDTO::getEmail).ifPresent(email -> criteria.andLikePrefix(Property.of(User::getEmail), email));
        opt.map(UserQueryDTO::getNickname).ifPresent(nickname -> criteria.andLikePrefix(Property.of(User::getNickname), nickname));
        query.and(criteria);
        return userRepository.page(query, new PageQuery(queryDTO)).map(UserEntityConverter::convert);
    }

}
