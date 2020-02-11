package com.fengwk.support.filesystem.domain;

import com.fengwk.support.domain.model.BasicEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BasicUcEntity信息默认由仓储层实现填充
 * 
 * @author fengwk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class FilesystemEntity extends BasicEntity<Long> {
    
    protected FilesystemEntity() {
        super(null);
    }

}
