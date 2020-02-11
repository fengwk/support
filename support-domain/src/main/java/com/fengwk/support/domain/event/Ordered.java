package com.fengwk.support.domain.event;

/**
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
    
    default int getOrder() {
        return DEFAULT_PRECEDENCE;
    }
    
}
