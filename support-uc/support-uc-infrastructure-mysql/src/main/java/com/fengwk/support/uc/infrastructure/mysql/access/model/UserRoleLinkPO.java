package com.fengwk.support.uc.infrastructure.mysql.access.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "UserRoleLink")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleLinkPO extends UcPO {

    Long userId;
    Long roleId;
    
}
