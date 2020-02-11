package com.fengwk.support.uc.restful.access.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.access.service.UserRoleApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/user-role")
@RestController
public class UserRoleController {

    @Autowired
    volatile UserRoleApiService userRoleApiService;
    
    @PostMapping
    public Result<Void> grantRole(
            @RequestParam("userId") Long userId, 
            @RequestParam("roleId") Long roleId) {
        userRoleApiService.grantRole(userId, roleId);
        return Results.success();
    }

    @DeleteMapping
    public Result<Void> revokeRole(
            @RequestParam("userId") Long userId, 
            @RequestParam("roleId") Long roleId) {
        userRoleApiService.revokeRole(userId, roleId);
        return Results.success();
    }
    
}
