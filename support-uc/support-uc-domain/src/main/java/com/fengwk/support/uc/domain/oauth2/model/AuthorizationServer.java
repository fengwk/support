package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import com.fengwk.support.core.domain.model.ValueObject;
import com.fengwk.support.core.exception.ExceptionCodes;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 授权服务器
 * 
 * @author fengwk
 */
@Slf4j
@Data
public class AuthorizationServer implements ValueObject {
    
    final URI baseUri;
    
    /**
     * 获取重定向至授权服务器的{@link URI}地址
     * 
     * @return
     */
    public URI redirectUri(AuthorizationServerRedirectRequest request) {
        request.checkCompletable();
        
        try {
            URIBuilder builder = new URIBuilder(baseUri);
            request.appendResponseType(builder);
            request.appendClientId(builder);
            request.appendRedirectUri(builder);
            request.appendScope(builder);
            request.appendState(builder);
            return builder.build();
        } catch (URISyntaxException e) {
            log.error("", e);
            throw ExceptionCodes.biz().create(e);
        }
    }
    
}