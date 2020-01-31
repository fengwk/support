package com.fengwk.support.uc.infrastructure.mysql.oauth2.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "Client")
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientPO extends UcPO {

    String name;
    String secret;
    String redirectRules;
    Integer accessExpiresIn;
    Integer refreshExpiresIn;
    Integer isExclusive;
    Integer tokenCountLimit;
    Integer isDisabled;

}
