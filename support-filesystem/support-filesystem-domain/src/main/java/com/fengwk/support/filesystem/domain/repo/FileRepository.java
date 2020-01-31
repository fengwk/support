package com.fengwk.support.filesystem.domain.repo;

import com.fengwk.support.filesystem.domain.model.File;

/**
 * 
 * @author fengwk
 */
public interface FileRepository {

    void add(File file);
    
    File getByAccessId(String accessId);
    
    File getByDigest(String digest);
    
}
