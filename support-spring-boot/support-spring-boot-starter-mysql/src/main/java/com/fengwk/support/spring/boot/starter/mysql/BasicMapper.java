package com.fengwk.support.spring.boot.starter.mysql;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author fengwk
 */
@RegisterMapper
public interface BasicMapper<T extends BasicPO<I>, I> extends Mapper<T> {

}
