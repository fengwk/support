package com.fengwk.support.uc.restful.user.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.convention.page.Page;
import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results;
import com.fengwk.support.spring.boot.starter.restful.auth.AuthorizationContext;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.service.UserPermissionApiService;
import com.fengwk.support.uc.api.access.service.UserRoleApiService;
import com.fengwk.support.uc.api.user.model.UserDTO;
import com.fengwk.support.uc.api.user.model.UserSearchDTO;
import com.fengwk.support.uc.api.user.model.UserUpdateDTO;
import com.fengwk.support.uc.api.user.service.UserApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/users")
@RestController
public class UserController {

    @Autowired
    volatile UserApiService userApiService;
    
    @Autowired
    volatile UserRoleApiService userRoleApiService;
    
    @Autowired
    volatile UserPermissionApiService userPermissionApiService;
    
    @GetMapping("/exists")
    public Result<Boolean> exists(@RequestParam("email") String email) {
        return Results.success(userApiService.existsByEmail(email));
    }
    
    @PatchMapping("/{userId}")
    public Result<UserDTO> updateSelective(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDTO updateDTO) {
        AuthorizationContext.checkAuthorized();
        updateDTO.setId(userId);
        return Results.success(userApiService.updateSelective(updateDTO));
    }
    
    @GetMapping("/search")
    public Result<Page<UserDTO>> search(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "nickname", required = false) String nickname) {
        AuthorizationContext.checkAuthorized();
        UserSearchDTO userQueryDTO = new UserSearchDTO();
        userQueryDTO.setPageNumber(pageNumber);
        userQueryDTO.setPageSize(pageSize);
        userQueryDTO.setEmail(StringUtils.trimToNull(email));
        userQueryDTO.setNickname(StringUtils.trimToNull(nickname));
        return Results.success(userApiService.search(userQueryDTO));
    }
    
    @GetMapping("/{userId}/roles")
    public Result<List<RoleDTO>> listRoles(@PathVariable("userId") Long userId) {
        AuthorizationContext.checkAuthorized();
        return Results.success(userRoleApiService.listRoles(userId));
    }
    
    @PostMapping("/{userId}/roles/{roleId}")
    public Result<Void> grantRole(
            @PathVariable("userId") Long userId, 
            @PathVariable("roleId") Long roleId) {
        AuthorizationContext.checkAuthorized();
        userRoleApiService.grantRole(userId, roleId);
        return Results.success();
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public Result<Void> revokeRole(
            @PathVariable("userId") Long userId, 
            @PathVariable("roleId") Long roleId) {
        AuthorizationContext.checkAuthorized();
        userRoleApiService.revokeRole(userId, roleId);
        return Results.success();
    }

    @GetMapping("/permissions")
    public Result<Boolean> verify(@RequestParam("path") @NotBlank String path) {
        long userId = AuthorizationContext.getUserId();
        return Results.success(userPermissionApiService.verify(userId, path));
    }
    
}
