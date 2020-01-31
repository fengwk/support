package com.fengwk.support.uc.web.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;
import com.fengwk.support.spring.boot.starter.web.trace.HttpTrace;
import com.fengwk.support.uc.api.access.service.UserPermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/userPermission")
@RestController
public class UserPermissionController {

    @Autowired
    volatile UserPermissionApiService userPermissionApiService;

    @PostMapping("/verify")
    public Response<Boolean> verify(@RequestParam("user-id") long userId, @RequestParam("applied-path") @NotBlank String appliedPath) {
        return Responses.success(userPermissionApiService.verify(userId, appliedPath));
    }
    
}
