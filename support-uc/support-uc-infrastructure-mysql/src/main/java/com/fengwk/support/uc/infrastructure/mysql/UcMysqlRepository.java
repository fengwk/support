package com.fengwk.support.uc.infrastructure.mysql;

import com.fengwk.support.spring.boot.starter.mysql.BasicConverter;
import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;
import com.fengwk.support.spring.boot.starter.mysql.convention.QueryConventionRepository;
import com.fengwk.support.uc.domain.UcEntity;

/**
 * 
 * @author fengwk
 */
public abstract class UcMysqlRepository<E extends UcEntity, P extends UcPO> extends QueryConventionRepository<E, P, Long> {

    public UcMysqlRepository(BasicMapper<P, Long> basicMapper, BasicConverter<E, P, Long> converter) {
        super(basicMapper, converter);
    }

}
