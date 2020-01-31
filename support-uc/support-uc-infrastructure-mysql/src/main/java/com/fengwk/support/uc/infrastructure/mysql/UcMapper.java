package com.fengwk.support.uc.infrastructure.mysql;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * 
 * @author fengwk
 */
@RegisterMapper
public interface UcMapper<T extends UcPO> extends BasicMapper<T, Long> {

}
