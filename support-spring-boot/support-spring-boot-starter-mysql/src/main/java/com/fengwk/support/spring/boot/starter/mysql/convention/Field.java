package com.fengwk.support.spring.boot.starter.mysql.convention;

import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.reflection.Reflections;

/**
 * 
 * @author fengwk
 */
public class Field {

    final Fn<?, ?> fn;
    final String name;
    
    Field(Fn<?, ?> fn) {
        this.fn = fn;
        this.name = Reflections.fnToFieldName(fn);
    }
    
    public String name() {
        return name;
    }
    
}
