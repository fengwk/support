package com.fengwk.support.filesystem.api.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fengwk.support.filesystem.api.model.FileInputDTO;

/**
 * 
 * @author fengwk
 */
public interface FileApiService {

    String upload(@NotNull @Valid FileInputDTO fileInputDTO);
    
    String getUri(@NotBlank String accessId);
    
}
