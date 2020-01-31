package com.fengwk.support.core.result;

/**
 * 
 * @author fengwk
 */
public interface Result {
    
    /**
     * 是否成功
     * 
     * @return
     */
    boolean isSuccess();
    
    /**
     * 结果描述消息
     * 
     * @return
     */
    String getMessage();

}
