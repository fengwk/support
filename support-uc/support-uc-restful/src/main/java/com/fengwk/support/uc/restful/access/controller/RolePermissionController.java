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
import com.fengwk.support.uc.api.access.service.RolePermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/role-permission")
@RestController
public class RolePermissionController {

    @Autowired
    volatile RolePermissionApiService rolePermissionApiService;

    @PostMapping
    public Result<Void> grantPermission(
            @RequestParam("roleId") Long roleId, 
            @RequestParam("permissionId") Long permissionId) {
        rolePermissionApiService.grantPermission(roleId, permissionId);
        return Results.success();
    }

    @DeleteMapping
    public Result<Void> revokePermission(
            @RequestParam("roleId") Long roleId, 
            @RequestParam("permissionId") Long permissionId) {
        rolePermissionApiService.revokePermission(roleId, permissionId);
        return Results.success();
    }
    
}
