package com.fengwk.support.filesystem.infrastructure.mysql;

import com.fengwk.support.filesystem.domain.FilesystemEntity;
import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;

/**
 * 
 * @author fengwk
 */
public interface FilesystemConverter<E extends FilesystemEntity, P extends FilesystemPO> extends Converter<E, P, Long> {

}
