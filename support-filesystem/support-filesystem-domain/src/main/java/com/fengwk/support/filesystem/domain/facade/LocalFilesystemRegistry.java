package com.fengwk.support.filesystem.domain.facade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fengwk.support.core.domain.exception.DomainException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Component
public class LocalFilesystemRegistry {

    final Map<String, LocalFilesystem> registry = new ConcurrentHashMap<>();
    
    @Autowired
    LocalFilesystemSelectStrategy selectStrategy;
    
    public void register(LocalFilesystem filesystem) {
        registry.put(filesystem.getId(), filesystem);
    }

    public LocalFilesystem get(String filesystemId) {
        LocalFilesystem filesystem = registry.get(filesystemId);
        if (filesystem == null) {
            log.error("指定的文件系统不存在,filesystemId={}.", filesystemId);
            throw new DomainException("指定的文件系统不存在.");
        }
        return filesystem;
    }
    
    public LocalFilesystem selectOne() {
        return selectStrategy.select(registry);
    }
    
}
