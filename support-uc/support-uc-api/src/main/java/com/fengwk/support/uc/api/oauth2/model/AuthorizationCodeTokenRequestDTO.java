package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权码模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationCodeTokenRequestDTO extends BasicTokenRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8987661387803399655L;
    
    /**
     * 授权码
     */
    @NotBlank
    String code;
    
}
