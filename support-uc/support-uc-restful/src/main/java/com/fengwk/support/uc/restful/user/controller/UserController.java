package com.fengwk.support.uc.restful.user.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.page.Page;
import com.fengwk.support.core.result.Result;
import com.fengwk.support.core.result.Results;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.user.model.UserEntityDTO;
import com.fengwk.support.uc.api.user.model.UserQueryDTO;
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
    
    @GetMapping("/exists")
    public Result<Boolean> exists(@RequestParam("email") String email) {
        return Results.success(userApiService.existsByEmail(email));
    }
    
    @PutMapping("/{userId}")
    public Result<UserEntityDTO> update(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO updateDTO) {
        return Results.success(userApiService.updateSelective(updateDTO));
    }
    
    @GetMapping("/search")
    public Result<Page<UserEntityDTO>> search(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "nickname", required = false) String nickname) {
        UserQueryDTO userQueryDTO = new UserQueryDTO();
        userQueryDTO.setPageNumber(pageNumber);
        userQueryDTO.setPageSize(pageSize);
        userQueryDTO.setEmail(StringUtils.trimToNull(email));
        userQueryDTO.setNickname(StringUtils.trimToNull(nickname));
        return Results.success(userApiService.query(userQueryDTO));
    }

}
