package com.fengwk.support.uc.api.oauth2.model;

import com.fengwk.support.core.convention.page.PageQueryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientSearchDTO extends PageQueryDTO {

    String name;
    
}
