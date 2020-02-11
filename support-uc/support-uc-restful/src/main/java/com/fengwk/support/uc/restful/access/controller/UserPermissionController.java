package com.fengwk.support.uc.restful.access.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.access.service.UserPermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/user-permission")
@RestController
public class UserPermissionController {

    @Autowired
    volatile UserPermissionApiService userPermissionApiService;

    @PostMapping("/verify")
    public Result<Boolean> verify(@RequestParam("userId") Long userId, @RequestParam("path") @NotBlank String path) {
        return Results.success(userPermissionApiService.verify(userId, path));
    }
    
}
