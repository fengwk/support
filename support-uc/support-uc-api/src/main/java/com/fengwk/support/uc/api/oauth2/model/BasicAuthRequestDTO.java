package com.fengwk.support.uc.api.oauth2.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 授权认证所必需的信息
 * 
 * @author fengwk
 */
@Data
abstract class BasicAuthRequestDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3111595974614245508L;

    /**
     * 授权认证响应类型
     */
    @NotBlank
    protected String responseType;
    
    /**
     * 客户端id
     */
    @NotNull
    protected Long clientId;
    
    /**
     * 授权认证后的重定向地址
     */
    @NotBlank
    protected String redirectUri;
    
    /**
     * 授权范围
     */
    @NotBlank
    protected String scope;
    
    /**
     * 状态
     */
    protected String state;
    
}
