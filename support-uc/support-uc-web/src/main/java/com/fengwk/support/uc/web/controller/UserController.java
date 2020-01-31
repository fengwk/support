package com.fengwk.support.uc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;
import com.fengwk.support.spring.boot.starter.web.trace.HttpTrace;
import com.fengwk.support.uc.api.user.service.UserApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    volatile UserApiService userApiService;
    
    @PostMapping("/existsEmail")
    public Response<Boolean> existsByEmail(@RequestParam("email") String email) {
        return Responses.success(userApiService.existsByEmail(email));
    }

}
