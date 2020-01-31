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
public class EmailAndRandomAuthRequestDTO extends BasicAuthRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5440202431743040758L;

    /**
     * 邮箱
     */
    @NotBlank
    String email;

    /**
     * 验证码
     */
    @NotBlank
    String random;
    
}
