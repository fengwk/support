package com.fengwk.support.filesystem.domain.model;

import java.io.InputStream;

import com.fengwk.support.domain.model.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FileInput implements ValueObject {

    protected final InputStream input;
    protected final String originalName;
    
}
