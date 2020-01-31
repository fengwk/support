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
import com.fengwk.support.uc.api.user.model.EmailAndRandomResetPasswordResetDTO;
import com.fengwk.support.uc.api.user.service.ResetApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/reset")
@RestController
public class ResetController {

    @Autowired
    volatile ResetApiService resetApiService;
    
    @PostMapping("/sendRandom")
    public Response<Integer> sendEmailRandom(@RequestParam("email") String email) {
        return Responses.success(resetApiService.sendEmailRandom(email));
    }
    
    @PostMapping("/verifyRandom")
    public Response<Boolean> verifyEmailRandom(@RequestBody EmailAndRandomDTO emailAndRandomDTO) {
        return Responses.success(resetApiService.verifyEmailRandom(emailAndRandomDTO));
    }

    @PostMapping
    public Response<String> resetPasswordAndGrant(@RequestBody EmailAndRandomResetPasswordResetDTO resetDTO) {
        return Responses.success(resetApiService.resetPasswordAndAuthorize(resetDTO));
    }

}
