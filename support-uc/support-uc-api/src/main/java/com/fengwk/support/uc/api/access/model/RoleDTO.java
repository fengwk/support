package com.fengwk.support.uc.api.access.model;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class RoleDTO {

    Long id;
    
    String name;
    
    List<Long> permissionIds;

}
