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
import com.fengwk.support.uc.api.oauth2.model.AuthorizationCodeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.AuthorizationServerRedirectRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RefreshTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RevokeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.OAuth2ApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/oauth2")
@RestController
public class OAuth2Controller {
    
    @Autowired
    volatile OAuth2ApiService oauth2ApiService;

    @PostMapping("/sendRandom")
    public Response<Integer> sendEmailRandom(@RequestParam("email") String email) {
        return Responses.success(oauth2ApiService.sendEmailRandom(email));
    }

    @PostMapping("/redirectUri")
    public Response<String> getUri(@RequestBody AuthorizationServerRedirectRequestDTO requestDTO) {
        return Responses.success(oauth2ApiService.redirectUri(requestDTO));
    }

    @PostMapping("/authorizeByEmailAndPassword")
    public Response<String> authorize(@RequestBody EmailAndPasswordAuthRequestDTO requestDTO) {
        return Responses.success(oauth2ApiService.authorizationCodeMode().authorize(requestDTO));
    }

    @PostMapping("/authorizeByEmailAndRandom")
    public Response<String> authorize(@RequestBody EmailAndRandomAuthRequestDTO requestDTO) {
        return Responses.success(oauth2ApiService.authorizationCodeMode().authorize(requestDTO));
    }

    @PostMapping("/token")
    public Response<TokenDTO> token(@RequestBody AuthorizationCodeTokenRequestDTO requestDTO) {
        return Responses.success(oauth2ApiService.authorizationCodeMode().token(requestDTO));
    }

    @PostMapping("/refresh")
    public Response<TokenDTO> refresh(@RequestBody RefreshTokenRequestDTO requestDTO) {
        return Responses.success(oauth2ApiService.authorizationCodeMode().token(requestDTO));
    }

    @PostMapping("/revoke")
    public Response<Void> revoke(@RequestBody RevokeTokenRequestDTO requestDTO) {
        oauth2ApiService.revoke(requestDTO);
        return Responses.success();
    }
    
}
