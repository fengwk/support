package com.fengwk.support.filesystem.domain.model;

import com.fengwk.support.core.domain.model.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author fengwk
 */
@AllArgsConstructor
@Data
public class LocalFile implements ValueObject {

    final String path;
    
}
