package com.fengwk.support.filesystem.api.model;

import java.io.InputStream;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author fengwk
 */
@AllArgsConstructor
@Data
public class FileInputDTO {

    @NotNull
    final InputStream input;
    final String originalName;
    
}
