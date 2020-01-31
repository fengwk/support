package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailAndPasswordTokenRequestDTO extends BasicTokenRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1824039005695663094L;
    
    /**
     * 邮箱
     */
    @NotBlank
    String email;
    
    /**
     * 密码
     */
    @NotBlank
    String password;
    
}
