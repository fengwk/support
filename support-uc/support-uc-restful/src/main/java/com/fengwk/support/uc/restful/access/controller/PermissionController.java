package com.fengwk.support.uc.restful.access.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.page.PageQueryDTO;
import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.access.model.PermissionCreateDTO;
import com.fengwk.support.uc.api.access.model.PermissionDTO;
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
        return Results.success(permissionApiService.create(permissionAddDTO));
    }

    @DeleteMapping("/{permissionId}")
    public Result<Void> remove(@PathVariable("permissionId") Long permissionId) {
        permissionApiService.remove(permissionId);
        return Results.success();
    }

    @PutMapping("/{permissionId}")
    public Result<Void> update(
            @PathVariable("permissionId") Long permissionId,
            @RequestParam("name") String name,
            @RequestParam("path") String path) {
        PermissionUpdateDTO permissionUpdateDTO = new PermissionUpdateDTO();
        permissionUpdateDTO.setId(permissionId);
        permissionUpdateDTO.setName(name);
        permissionUpdateDTO.setPath(path);
        permissionApiService.update(permissionUpdateDTO);
        return Results.success();
    }

    @GetMapping
    public Result<List<PermissionDTO>> list(@RequestParam("roleId") Long roleId) {
        return Results.success(permissionApiService.list(roleId));
    }

    @GetMapping("/page")
    public Result<Page<PermissionDTO>> page(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        PageQueryDTO pageQueryDTO = new PageQueryDTO();
        pageQueryDTO.setPageNumber(pageNumber);
        pageQueryDTO.setPageSize(pageSize);
        return Results.success(permissionApiService.page(pageQueryDTO));
    }
    
}
