package com.fengwk.support.uc.restful.access.controller;

import java.util.List;

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
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleSearchDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RoleApiService;
import com.fengwk.support.uc.api.access.service.RolePermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/roles")
@RestController
public class RoleController {

    @Autowired
    volatile RoleApiService roleApiService;
    
    @Autowired
    volatile RolePermissionApiService rolePermissionApiService;
    
    @PostMapping
    public Result<RoleDTO> create(@RequestBody RoleCreateDTO roleAddDTO) {
        AuthorizationContext.checkAuthorized();
        return Results.success(roleApiService.create(roleAddDTO));
    }

    @DeleteMapping("/{roleId}")
    public Result<Void> remove(@PathVariable("roleId") long roleId) {
        AuthorizationContext.checkAuthorized();
        roleApiService.remove(roleId);
        return Results.success();
    }

    @PatchMapping("/{roleId}")
    public Result<RoleDTO> updateSelective(
            @PathVariable("roleId") long roleId,
            @RequestBody RoleUpdateDTO roleUpdateDTO) {
        AuthorizationContext.checkAuthorized();
        roleUpdateDTO.setId(roleId);
        return Results.success(roleApiService.updateSelective(roleUpdateDTO));
    }

    @GetMapping("/search")
    public Result<Page<RoleDTO>> search(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "name", required = false) String name) {
        AuthorizationContext.checkAuthorized();
        RoleSearchDTO searchDTO = new RoleSearchDTO();
        searchDTO.setPageNumber(pageNumber);
        searchDTO.setPageSize(pageSize);
        searchDTO.setName(StringUtils.trimToNull(name));
        return Results.success(roleApiService.search(searchDTO));
    }
    
    @GetMapping("/{roleId}/permissions")
    public Result<List<PermissionDTO>> listPermissions(@PathVariable("roleId") Long roleId) {
        AuthorizationContext.checkAuthorized();
        return Results.success(rolePermissionApiService.listPermissions(roleId));
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    public Result<Void> grantPermission(
            @PathVariable("roleId") Long roleId, 
            @PathVariable("permissionId") Long permissionId) {
        AuthorizationContext.checkAuthorized();
        rolePermissionApiService.grantPermission(roleId, permissionId);
        return Results.success();
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    public Result<Void> revokePermission(
            @PathVariable("roleId") Long roleId, 
            @PathVariable("permissionId") Long permissionId) {
        AuthorizationContext.checkAuthorized();
        rolePermissionApiService.revokePermission(roleId, permissionId);
        return Results.success();
    }
    
}
