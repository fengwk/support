package com.fengwk.support.uc.api.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权服务器重定向请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationServerRedirectRequestDTO extends BasicAuthRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4793976157206215173L;
    
}
