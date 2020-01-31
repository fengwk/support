package com.fengwk.support.spring.boot.starter.mysql;

import java.util.List;

import com.fengwk.support.spring.boot.starter.mysql.BasicMapper;

/**
 * 
 * @author fengwk
 */
public interface TestMapper extends BasicMapper<TestPO, Long> {

    List<TestPO> listByName(String name);
    
}
