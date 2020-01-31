package com.fengwk.support.filesystem.infrastructure.mysql;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * 
 * @author fengwk
 */
@RegisterMapper
public interface FilesystemMapper<P extends FilesystemPO> extends BasicMapper<P, Long> {

}
