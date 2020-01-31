package com.fengwk.support.filesystem.domain.model;

import java.io.InputStream;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LocalFileInput extends FileInput {

    final String type;
    final String digest;
    final long size;
    
    public LocalFileInput(InputStream input, String originalName, String type, String digest, long size) {
        super(input, originalName);
        this.type = type;
        this.digest = digest;
        this.size = size;
    }

}
