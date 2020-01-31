package com.fengwk.support.uc.infrastructure.mysql.oauth2.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "AuthorizationCode")
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationCodePO extends UcPO {

    String code;
    Integer expiresIn;
    Integer isUsed;
    
    String responseType;
    Long clientId;
    String redirectUri;
    String scope;
    String state;
    Long userId;
    
}
