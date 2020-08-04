package com.fengwk.support.core.domain.event;

/**
 * 优先级排序
 * 
 * @author fengwk
 */
public interface Ordered {

    /**
     * 最高优先级
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
    
    /**
     * 默认优先级
     */
    int DEFAULT_PRECEDENCE = 5;
    
    /**
     * 获取当前对象优先级，返回值越小优先级越高
     * 
     * @return
     */
    default int getOrder() {
        return DEFAULT_PRECEDENCE;
    }
    
}
