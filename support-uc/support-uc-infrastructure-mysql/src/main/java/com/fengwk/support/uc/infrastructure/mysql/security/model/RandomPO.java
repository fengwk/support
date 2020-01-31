package com.fengwk.support.uc.infrastructure.mysql.security.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "Random")
@EqualsAndHashCode(callSuper = true)
@Data
public class RandomPO extends UcPO {

    Integer way;
    Integer type;
    String target;
    String value;
    Integer expiresIn;
    Integer status;
    
}
