package com.fengwk.support.uc.infrastructure.mysql;

import com.fengwk.support.spring.boot.starter.mysql.convention.Converter;
import com.fengwk.support.uc.domain.UcEntity;

/**
 * 
 * @author fengwk
 */
public interface UcConverter<E extends UcEntity, P extends UcPO> extends Converter<E, P, Long> {

}
