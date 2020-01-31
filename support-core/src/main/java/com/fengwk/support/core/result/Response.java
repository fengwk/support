package com.fengwk.support.core.result;

import java.io.Serializable;

/**
 * 结果
 * 
 * @author fengwk
 */
public interface Response<T> extends Serializable, Result {

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
