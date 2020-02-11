package com.fengwk.support.filesystem.infrastructure.mysql;

import com.fengwk.support.filesystem.domain.FilesystemEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;

/**
 * 
 * @author fengwk
 */
public interface FilesystemConverter<E extends FilesystemEntity, P extends FilesystemPO> extends BasicConverter<E, P, Long> {

}
