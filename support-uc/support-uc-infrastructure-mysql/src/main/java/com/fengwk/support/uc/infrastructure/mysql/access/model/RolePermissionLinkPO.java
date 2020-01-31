package com.fengwk.support.uc.infrastructure.mysql.access.model;

import javax.persistence.Table;

import com.fengwk.support.uc.infrastructure.mysql.UcPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "RolePermissionLink")
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePermissionLinkPO extends UcPO {

    Long roleId;
    Long permissionId;
    
}
