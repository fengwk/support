package com.fengwk.support.filesystem.domain.facade;

import java.util.Map;

/**
 * 
 * @author fengwk
 */
public interface LocalFilesystemSelectStrategy {

    LocalFilesystem select(Map<String, LocalFilesystem> registry);
    
}
