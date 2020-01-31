package com.fengwk.support.uc.application.access.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.uc.api.access.service.UserPermissionApiService;
import com.fengwk.support.uc.domain.access.service.UserPermissionService;

/**
 * 
 * @author fengwk
 */
@Validated
@Transactional
@Primary
@Service
public class UserPermissionApiServiceImpl implements UserPermissionApiService {

    @Autowired
    volatile UserPermissionService userPermissionService;
    
    @Override
    public boolean verify(long userId, String testPath) {
        return userPermissionService.verify(userId, testPath);
    }

}
