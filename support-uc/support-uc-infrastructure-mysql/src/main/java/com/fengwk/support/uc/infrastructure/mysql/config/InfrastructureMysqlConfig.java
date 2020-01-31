package com.fengwk.support.uc.infrastructure.mysql.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author fengwk
 */
@MapperScan("com.fengwk.support.uc.infrastructure.mysql.**.mapper")
@ComponentScan("com.fengwk.support.uc.infrastructure.mysql")
@Configuration
public class InfrastructureMysqlConfig {

}
