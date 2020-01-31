package com.fengwk.support.uc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.result.Response;
import com.fengwk.support.core.result.Responses;
import com.fengwk.support.spring.boot.starter.web.trace.HttpTrace;
import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomRegisterDTO;
import com.fengwk.support.uc.api.user.service.RegisterApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    volatile RegisterApiService registerApiService;

    @PostMapping("/sendRandom")
    public Response<Integer> sendEmailRandom(@RequestParam("email") String email) {
        return Responses.success(registerApiService.sendEmailRandom(email));
    }

    @PostMapping("/verifyRandom")
    public Response<Boolean> verifyEmailRandom(@RequestBody EmailAndRandomDTO emailAndRandomDTO) {
        return Responses.success(registerApiService.verifyEmailRandom(emailAndRandomDTO));
    }

    @PostMapping
    public Response<String> registerAndAuthorize(@RequestBody EmailAndRandomRegisterDTO registerDTO) {
        return Responses.success(registerApiService.registerAndAuthorize(registerDTO));
    }
    
}
