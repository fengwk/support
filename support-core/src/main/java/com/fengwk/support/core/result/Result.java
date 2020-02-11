package com.fengwk.support.core.result;

import java.io.Serializable;

/**
 * 结果
 * 
 * @author fengwk
 */
public interface Result<T> extends Serializable {

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
    
    /**
     * 获取结果码
     * 
     * @return
     */
    String getCode();
    
    /**
     * 获取结果数据
     * 
     * @return
     */
    T getData();
    
}
