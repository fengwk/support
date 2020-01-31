package com.fengwk.support.filesystem.infrastructure.mysql.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author fengwk
 */
@MapperScan("com.fengwk.support.filesystem.infrastructure.mysql.mapper")
@ComponentScan("com.fengwk.support.filesystem.infrastructure.mysql")
@Configuration
public class InfrastructureMysqlConfig {
    
}
