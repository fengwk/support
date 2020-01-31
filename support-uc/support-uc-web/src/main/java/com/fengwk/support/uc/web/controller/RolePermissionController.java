package com.fengwk.support.uc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;
import com.fengwk.support.spring.boot.starter.web.trace.HttpTrace;
import com.fengwk.support.uc.api.access.model.PermissionAddDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.model.RoleAddDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RolePermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/rolePermission")
@RestController
public class RolePermissionController {

    @Autowired
    volatile RolePermissionApiService rolePermissionApiService;
    
    @PostMapping("/createRole")
    public Response<RoleDTO> createRole(@RequestBody RoleAddDTO roleAddDTO) {
        return Responses.success(rolePermissionApiService.createRole(roleAddDTO));
    }

    @PostMapping("/removeRole")
    public Response<Void> removeRole(@RequestParam("roleId") long roleId) {
        rolePermissionApiService.removeRole(roleId);
        return Responses.success();
    }

    @PostMapping("/updateRole")
    public Response<Void> updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO) {
        rolePermissionApiService.updateRole(roleUpdateDTO);
        return Responses.success();
    }

    @PostMapping("/listRole")
    public Response<List<RoleDTO>> listRole(@RequestParam("userId") long userId) {
        return Responses.success(rolePermissionApiService.listRole(userId));
    }

    @PostMapping("/pageRole")
    public Response<Page<RoleDTO>> pageRole(@RequestBody PageQueryDTO pageQueryDTO) {
        return Responses.success(rolePermissionApiService.pageRole(pageQueryDTO));
    }

    @PostMapping("/createPermission")
    public Response<PermissionDTO> createPermission(@RequestBody PermissionAddDTO permissionAddDTO) {
        return Responses.success(rolePermissionApiService.createPermission(permissionAddDTO));
    }

    @PostMapping("/removePermission")
    public Response<Void> removePermission(@RequestParam("permissionId") long permissionId) {
        rolePermissionApiService.removePermission(permissionId);
        return Responses.success();
    }

    @PostMapping("/updatePermission")
    public Response<Void> updatePermission(@RequestBody PermissionUpdateDTO permissionUpdateDTO) {
        rolePermissionApiService.updatePermission(permissionUpdateDTO);
        return Responses.success();
    }

    @PostMapping("/listPermission")
    public Response<List<PermissionDTO>> listPermission(@RequestParam("roleId") long roleId) {
        return Responses.success(rolePermissionApiService.listPermission(roleId));
    }

    @PostMapping("/pagePermission")
    public Response<Page<PermissionDTO>> pagePermission(@RequestBody PageQueryDTO pageQueryDTO) {
        return Responses.success(rolePermissionApiService.pagePermission(pageQueryDTO));
    }

    @PostMapping("/grantRole")
    public Response<Void> grantRole(@RequestParam("userId") long userId, @RequestParam("roleId") long roleId) {
        rolePermissionApiService.grantRole(userId, roleId);
        return Responses.success();
    }

    @PostMapping("/revokeRole")
    public Response<Void> revokeRole(@RequestParam("userId") long userId, @RequestParam("roleId") long roleId) {
        rolePermissionApiService.revokeRole(userId, roleId);
        return Responses.success();
    }

    @PostMapping("/grantPermission")
    public Response<Void> grantPermission(@RequestParam("roleId") long roleId, @RequestParam("permissionId") long permissionId) {
        rolePermissionApiService.grantPermission(roleId, permissionId);
        return Responses.success();
    }

    @PostMapping("/revokePermission")
    public Response<Void> revokePermission(@RequestParam("roleId") long roleId, @RequestParam("permissionId") long permissionId) {
        rolePermissionApiService.revokePermission(roleId, permissionId);
        return Responses.success();
    }
    
}
