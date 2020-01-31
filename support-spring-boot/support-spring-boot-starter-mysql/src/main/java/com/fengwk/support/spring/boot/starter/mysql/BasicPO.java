package com.fengwk.support.spring.boot.starter.mysql;

import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * 
 * @author fengwk
 */
@Data
public abstract class BasicPO<I> {
	
	/**
	 * 主键
	 */
    @Id
    @KeySql(useGeneratedKeys = true)
	protected I id;
	
	/**
	 * 创建时间
	 */
	protected LocalDateTime createdTime;
	
	/**
	 * 修改时间
	 */
	protected LocalDateTime modifiedTime;
	
}
