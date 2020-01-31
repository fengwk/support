package com.fengwk.support.core.query;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
@Data
public class And implements Condition {

    List<Condition> conditions;
    
}
