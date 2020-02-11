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
import com.fengwk.support.uc.api.access.model.RoleCreateDTO;
import com.fengwk.support.uc.api.access.model.RoleDTO;
import com.fengwk.support.uc.api.access.model.RoleUpdateDTO;
import com.fengwk.support.uc.api.access.service.RoleApiService;

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
    
    @PostMapping
    public Result<RoleDTO> create(@RequestBody RoleCreateDTO roleAddDTO) {
        return Results.success(roleApiService.create(roleAddDTO));
    }

    @DeleteMapping("/{roleId}")
    public Result<Void> remove(@PathVariable("roleId") long roleId) {
        roleApiService.remove(roleId);
        return Results.success();
    }

    @PutMapping("/{roleId}")
    public Result<Void> update(
            @PathVariable("roleId") long roleId,
            @RequestParam("name") String name) {
        RoleUpdateDTO roleUpdateDTO = new RoleUpdateDTO();
        roleUpdateDTO.setId(roleId);
        roleUpdateDTO.setName(name);
        roleApiService.update(roleUpdateDTO);
        return Results.success();
    }

    @GetMapping
    public Result<List<RoleDTO>> list(@RequestParam("userId") long userId) {
        return Results.success(roleApiService.list(userId));
    }

    @GetMapping("/page")
    public Result<Page<RoleDTO>> page(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize) {
        PageQueryDTO pageQueryDTO = new PageQueryDTO();
        pageQueryDTO.setPageNumber(pageNumber);
        pageQueryDTO.setPageSize(pageSize);
        return Results.success(roleApiService.page(pageQueryDTO));
    }
    
}
