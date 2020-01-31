package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 授权认证信息
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailAndPasswordAuthRequestDTO extends BasicAuthRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1296415118217169401L;

    /**
     * 邮箱
     */
    @NotBlank
    String email;

    /**
     * 明文密码
     */
    @NotBlank
    String password;
    
}
