package com.fengwk.support.filesystem.infrastructure.mysql;

import com.fengwk.support.filesystem.domain.FilesystemEntity;
import com.fengwk.support.spring.boot.starter.mysql.convention.AbstractMysqlRepository;

/**
 * 
 * @author fengwk
 */
public abstract class FilesystemMysqlRepository<E extends FilesystemEntity, P extends FilesystemPO> extends AbstractMysqlRepository<E, P, Long> {

}
