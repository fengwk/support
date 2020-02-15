package com.fengwk.support.uc.api.oauth2.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class RedirectRuleDTO {

    @NotNull
    Integer mode;
    
    String value;
    
}
