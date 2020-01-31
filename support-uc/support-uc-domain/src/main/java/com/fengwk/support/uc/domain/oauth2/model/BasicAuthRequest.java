package com.fengwk.support.uc.domain.oauth2.model;

import java.net.URI;

import org.apache.http.client.utils.URIBuilder;

import com.fengwk.support.core.domain.model.ValueObject;
import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.uc.domain.oauth2.Constants;

import lombok.Data;

/**
 * 授权认证所必需的信息
 * 
 * @author fengwk
 */
@Data
abstract class BasicAuthRequest implements ValueObject {

    /**
     * 授权认证响应类型
     * 
     * @see Constants#RESPONSE_TYPE_CODE
     * @see Constants#RESPONSE_TYPE_TOKEN
     */
    protected final String responseType;
    
    /**
     * 客户端id
     */
    protected final long clientId;
    
    /**
     * 授权认证后的重定向地址
     */
    protected final URI redirectUri;
    
    /**
     * 授权范围
     */
    protected final String scope;
    
    /**
     * 状态
     */
    protected final String state;
    
    /**
     * 校验请求完整性,若不完整则抛出参数异常
     */
    public void checkCompletable() {
        Preconditions.notBlank(responseType, "授权认证响应类型不能为空");
        Preconditions.notNull(redirectUri, "授权认证重定向地址不能为空");
        Preconditions.notBlank(scope, "授权范围不能为空");
    }
    
    /**
     * 测试请求是否为授权码模式
     * https://b.com/oauth/authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read
     * 
     * @return
     */
    public boolean isAuthorizationCodeMode() {
        return Constants.RESPONSE_TYPE_CODE.equals(responseType);
    }
    
    /**
     * 测试请求是否为隐藏模式
     * https://b.com/oauth/authorize?response_type=token&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read
     * 
     * @return
     */
    public boolean isImplicitMode() {
        return Constants.RESPONSE_TYPE_TOKEN.equals(responseType);
    }
    
    void appendResponseType(URIBuilder builder) {
        builder.addParameter("response_type", responseType);
    }
    
    void appendClientId(URIBuilder builder) {
        builder.addParameter("client_id", String.valueOf(clientId));
    }
    
    void appendRedirectUri(URIBuilder builder) {
        builder.addParameter("redirect_uri", redirectUri.toASCIIString());
    }
    
    void appendScope(URIBuilder builder) {
        builder.addParameter("scope", scope);
    }
    
    void appendState(URIBuilder builder) {
        builder.addParameter("state", state);
    }
    
}
