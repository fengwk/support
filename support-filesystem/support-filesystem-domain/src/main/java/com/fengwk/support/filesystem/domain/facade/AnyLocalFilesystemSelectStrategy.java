package com.fengwk.support.filesystem.domain.facade;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.validation.annotation.Validated;

import com.fengwk.support.domain.exception.DomainException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
@Validated
public class AnyLocalFilesystemSelectStrategy implements LocalFilesystemSelectStrategy {

    @Override
    public LocalFilesystem select(Map<String, LocalFilesystem> registry) {
        if (MapUtils.isEmpty(registry)) {
            log.error("没有可用的文件系统");
            throw new DomainException("没有可用的文件系统");
        }
        return registry.entrySet().iterator().next().getValue();
    }

}
