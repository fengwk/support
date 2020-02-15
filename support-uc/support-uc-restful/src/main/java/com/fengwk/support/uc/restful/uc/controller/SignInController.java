package com.fengwk.support.uc.restful.uc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengwk.support.core.convention.result.Result;
import com.fengwk.support.core.convention.result.Results;
import com.fengwk.support.core.util.UuidUtils;
import com.fengwk.support.spring.boot.starter.restful.trace.HttpTrace;
import com.fengwk.support.uc.api.oauth2.model.AuthorizationCodeTokenRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.AuthorizationServerRedirectRequestDTO;
import com.fengwk.support.uc.api.oauth2.model.TokenDTO;
import com.fengwk.support.uc.api.oauth2.service.OAuth2ApiService;
import com.fengwk.support.uc.domain.oauth2.OAuth2Constants;

/**
 * 
 * @author fengwk
 */
@HttpTrace
@CrossOrigin
@RequestMapping("/v1/sign-in")
@RestController
public class SignInController {

    @Value("${uc.oauth2.clientId}")
    volatile Long clientId;
    
    @Value("${uc.oauth2.clientSecret}")
    volatile String clientSecret;
    
    @Autowired
    volatile OAuth2ApiService oauth2ApiService;
    
    @GetMapping
    public Result<String> oauth2(@RequestParam("redirectUri") String redirectUri) {
        AuthorizationServerRedirectRequestDTO requestDTO = new AuthorizationServerRedirectRequestDTO();
        requestDTO.setResponseType(OAuth2Constants.RESPONSE_TYPE_CODE);
        requestDTO.setClientId(this.clientId);
        requestDTO.setRedirectUri(redirectUri);
        requestDTO.setScope("normal");
        requestDTO.setState(UuidUtils.genShort());
        return Results.success(oauth2ApiService.redirectUri(requestDTO));
    }
    
    @PostMapping("/token")
    public Result<String> token(@RequestParam("code") String code) {
        AuthorizationCodeTokenRequestDTO requestDTO = new AuthorizationCodeTokenRequestDTO();
        requestDTO.setClientId(this.clientId);
        requestDTO.setClientSecret(this.clientSecret);
        requestDTO.setCode(code);
        requestDTO.setGrantType(OAuth2Constants.GRANT_TYPE_AUTHORIZATION_CODE);
        TokenDTO tokenDTO = oauth2ApiService.authorizationCodeMode().token(requestDTO);
        return Results.success(tokenDTO.getAccessToken());
    }
    
}
