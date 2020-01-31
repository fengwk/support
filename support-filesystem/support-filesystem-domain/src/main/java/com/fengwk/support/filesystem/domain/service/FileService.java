package com.fengwk.support.filesystem.domain.service;

import java.net.URI;

import com.fengwk.support.filesystem.domain.model.File;
import com.fengwk.support.filesystem.domain.model.FileInput;

/**
 * 
 * @author fengwk
 */
public interface FileService {

    File upload(FileInput fileInput);
    
    URI getUri(String accessId);
    
}
