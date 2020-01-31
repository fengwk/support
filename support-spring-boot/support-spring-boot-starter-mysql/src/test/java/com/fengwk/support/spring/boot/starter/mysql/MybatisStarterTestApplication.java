package com.fengwk.support.spring.boot.starter.mysql;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author fengwk
 */
@MapperScan(basePackages = "com.fengwk.support.spring.boot.starter.mysql")
@SpringBootApplication
public class MybatisStarterTestApplication {

}
