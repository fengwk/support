package com.fengwk.support.filesystem.infrastructure.mysql;

import com.fengwk.support.filesystem.domain.FilesystemEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.QueryConventionRepository;

/**
 * 
 * @author fengwk
 */
public abstract class FilesystemMysqlRepository<E extends FilesystemEntity, P extends FilesystemPO> extends QueryConventionRepository<E, P, Long> {

    public FilesystemMysqlRepository(BasicMapper<P, Long> basicMapper, BasicConverter<E, P, Long> basicConverter) {
        super(basicMapper, basicConverter);
    }

}
