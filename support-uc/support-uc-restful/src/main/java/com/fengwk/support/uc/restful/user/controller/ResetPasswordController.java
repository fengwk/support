package com.fengwk.support.uc.restful.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.user.model.EmailAndRandomDTO;
import com.fengwk.support.uc.api.user.model.EmailAndRandomResetPasswordResetDTO;
import com.fengwk.support.uc.api.user.service.ResetApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/reset-password")
@RestController
public class ResetPasswordController {

    @Autowired
    volatile ResetApiService resetApiService;
    
    @PostMapping("/random")
    public Result<Integer> sendEmailRandom(@RequestParam("email") String email) {
        return Results.success(resetApiService.sendEmailRandom(email));
    }
    
    @PatchMapping("/random/verify")
    public Result<Boolean> verifyEmailRandom(@RequestBody EmailAndRandomDTO emailAndRandomDTO) {
        return Results.success(resetApiService.verifyEmailRandom(emailAndRandomDTO));
    }

    @PostMapping
    public Result<String> resetPasswordAndAuthorize(@RequestBody EmailAndRandomResetPasswordResetDTO resetDTO) {
        return Results.success(resetApiService.resetPasswordAndAuthorize(resetDTO));
    }

}
