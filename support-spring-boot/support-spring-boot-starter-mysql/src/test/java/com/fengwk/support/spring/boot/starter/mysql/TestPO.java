package com.fengwk.support.spring.boot.starter.mysql;

import javax.persistence.Table;

import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author fengwk
 */
@Table(name = "Test")
@EqualsAndHashCode(callSuper = true)
@Data
public class TestPO extends BasicPO<Long> {

    String name;
    
}
