package com.fengwk.support.uc.restful.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results;
import com.fengwk.support.spring.boot.starter.restful.auth.AuthorizationContext;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.oauth2.model.AuthorizationCodeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.AuthorizationServerRedirectRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndPasswordAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.EmailAndRandomAuthRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.RefreshTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.OAuth2ApiService;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/oauth2")
@RestController
public class OAuth2Controller {
    
    @Autowired
    volatile OAuth2ApiService oauth2ApiService;

    @PostMapping("/random")
    public Result<Integer> sendEmailRandom(@RequestParam("email") String email) {
        return Results.success(oauth2ApiService.sendEmailRandom(email));
    }

    @GetMapping("/redirect-uri")
    public Result<String> redirectUri(
            @RequestParam("responseType") String responseType,
            @RequestParam("clientId") Long clientId,
            @RequestParam("redirectUri") String redirectUri,
            @RequestParam("scope") String scope,
            @RequestParam(value = "state", required = false) String state) {
        AuthorizationServerRedirectRequestDTO requestDTO = new AuthorizationServerRedirectRequestDTO();
        requestDTO.setResponseType(responseType);
        requestDTO.setClientId(clientId);
        requestDTO.setRedirectUri(redirectUri);
        requestDTO.setScope(scope);
        requestDTO.setState(state);
        return Results.success(oauth2ApiService.redirectUri(requestDTO));
    }

    @PostMapping("/code/email-and-password")
    public Result<String> authorize(@RequestBody EmailAndPasswordAuthRequestDTO requestDTO) {
        return Results.success(oauth2ApiService.authorizationCodeMode().authorize(requestDTO));
    }

    @PostMapping("/code/email-and-random")
    public Result<String> authorize(@RequestBody EmailAndRandomAuthRequestDTO requestDTO) {
        return Results.success(oauth2ApiService.authorizationCodeMode().authorize(requestDTO));
    }

    @PostMapping("/token")
    public Result<TokenDTO> token(@RequestBody AuthorizationCodeTokenRequestDTO requestDTO) {
        return Results.success(oauth2ApiService.authorizationCodeMode().token(requestDTO));
    }

    @PatchMapping("/token")
    public Result<TokenDTO> refresh(@RequestBody RefreshTokenRequestDTO requestDTO) {
        return Results.success(oauth2ApiService.authorizationCodeMode().token(requestDTO));
    }

    @DeleteMapping("/token")
    public Result<Void> revoke() {
        String accessToken = AuthorizationContext.getAccessToken();
        oauth2ApiService.revoke(accessToken);
        return Results.success();
    }
    
}
