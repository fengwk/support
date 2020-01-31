package com.fengwk.support.uc.infrastructure.mysql.oauth2.model;

import java.time.LocalDateTime;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "Token")
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenPO extends UcPO {

    Long clientId;
    Long userId;
    String grantType;
    String scope;
    String tokenType;
    
    String accessToken;
    Integer accessExpiresIn;
    LocalDateTime accessCreatedTime;

    String refreshToken;
    Integer refreshExpiresIn;
    LocalDateTime refreshCreatedTime;
    
    Integer isInvalid;

}
