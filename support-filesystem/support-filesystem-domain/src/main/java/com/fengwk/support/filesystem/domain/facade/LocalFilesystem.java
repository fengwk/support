package com.fengwk.support.filesystem.domain.facade;

import java.net.URI;

import com.fengwk.support.filesystem.domain.model.LocalFile;
import com.fengwk.support.filesystem.domain.model.LocalFileInput;

/**
 * 
 * @author fengwk
 */
public interface LocalFilesystem {

    String getId();
    
    LocalFile store(LocalFileInput localFileInput);
    
    URI parse(LocalFile localFile);
    
}
