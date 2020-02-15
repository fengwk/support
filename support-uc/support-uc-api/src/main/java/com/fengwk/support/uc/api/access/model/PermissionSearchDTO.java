package com.fengwk.support.uc.api.access.model;

import com.fengwk.support.core.convention.page.PageQueryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionSearchDTO extends PageQueryDTO {

    String name;
    
}
