package com.fengwk.support.uc.infrastructure.mysql;

import com.fengwk.support.spring.boot.starter.mysql.convention.AbstractMysqlRepository;
import com.fengwk.support.uc.domain.UcEntity;

/**
 * 
 * @author fengwk
 */
public abstract class UcMysqlRepository<E extends UcEntity, P extends UcPO> extends AbstractMysqlRepository<E, P, Long> {

}
