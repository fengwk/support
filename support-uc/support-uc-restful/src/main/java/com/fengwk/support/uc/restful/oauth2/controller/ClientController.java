package com.fengwk.support.uc.restful.oauth2.controller;

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
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.oauth2.model.ClientCreateDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientSearchDTO;
import com.fengwk.support.uc.api.oauth2.model.ClientUpdateDTO;
import com.fengwk.support.uc.api.oauth2.service.ClientApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/clients")
@RestController
public class ClientController {

    @Autowired
    volatile ClientApiService clientApiService;
    
    @PostMapping
    public Result<ClientDTO> create(@RequestBody ClientCreateDTO createDTO) {
        return Results.success(clientApiService.create(createDTO));
    }
    
    @DeleteMapping("/{clientId}")
    public Result<Void> remove(@PathVariable("clientId") Long clientId) {
        clientApiService.remove(clientId);
        return Results.success();
    }
    
    @PatchMapping("/{clientId}")
    public Result<ClientDTO> updateSelective(
            @PathVariable("clientId") Long clientId,
            @RequestBody ClientUpdateDTO updateDTO) {
        updateDTO.setId(clientId);
        return Results.success(clientApiService.updateSelective(updateDTO));
    }
    
    @PatchMapping("/{clientId}/secret")
    public Result<String> refreshSecret(@PathVariable("clientId") Long clientId) {
        return Results.success(clientApiService.refreshSecret(clientId));
    }
    
    @GetMapping("/search")
    public Result<Page<ClientDTO>> search(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "name", required = false) String name) {
        ClientSearchDTO searchDTO = new ClientSearchDTO();
        searchDTO.setPageNumber(pageNumber);
        searchDTO.setPageSize(pageSize);
        searchDTO.setName(name);
        return Results.success(clientApiService.search(searchDTO));
    }
    
}
