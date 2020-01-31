package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 刷新令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshTokenRequestDTO extends BasicTokenRequestDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7214727870637605818L;
    
    /**
     * 刷新令牌
     */
    @NotBlank
    String refreshToken;

}
