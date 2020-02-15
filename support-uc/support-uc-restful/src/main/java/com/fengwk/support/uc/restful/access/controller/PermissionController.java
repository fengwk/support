package com.fengwk.support.uc.restful.access.controller;

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
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
import com.fengwk.support.uc.api.access.model.PermissionSearchDTO;
import com.fengwk.support.uc.api.access.model.PermissionUpdateDTO;
import com.fengwk.support.uc.api.access.service.PermissionApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/permissions")
@RestController
public class PermissionController {

    @Autowired
    volatile PermissionApiService permissionApiService;
    
    @PostMapping
    public Result<PermissionDTO> create(@RequestBody PermissionCreateDTO permissionAddDTO) {
        AuthorizationContext.checkAuthorized();
        return Results.success(permissionApiService.create(permissionAddDTO));
    }

    @DeleteMapping("/{permissionId}")
    public Result<Void> remove(@PathVariable("permissionId") Long permissionId) {
        AuthorizationContext.checkAuthorized();
        permissionApiService.remove(permissionId);
        return Results.success();
    }

    @PatchMapping("/{permissionId}")
    public Result<PermissionDTO> updateSelective(
            @PathVariable("permissionId") Long permissionId,
            @RequestBody PermissionUpdateDTO updateDTO) {
        AuthorizationContext.checkAuthorized();
        updateDTO.setId(permissionId);
        return Results.success(permissionApiService.updateSelective(updateDTO));
    }

    @GetMapping("/search")
    public Result<Page<PermissionDTO>> search(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "name", required = false) String name) {
        AuthorizationContext.checkAuthorized();
        PermissionSearchDTO searchDTO = new PermissionSearchDTO();
        searchDTO.setPageNumber(pageNumber);
        searchDTO.setPageSize(pageSize);
        searchDTO.setName(StringUtils.trimToNull(name));
        return Results.success(permissionApiService.search(searchDTO));
    }
    
}
