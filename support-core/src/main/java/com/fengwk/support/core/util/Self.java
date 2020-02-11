package com.fengwk.support.core.util;

/**
 * 模拟self类型
 * 
 * @author fengwk
 */
public interface Self<T extends Self<T>> {

    T self();
    
}
