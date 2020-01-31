package com.fengwk.support.uc.api.oauth2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 凭证模式令牌请求
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientCredentialsTokenRequestDTO extends BasicTokenRequestDTO {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1199624008909011082L;

}
