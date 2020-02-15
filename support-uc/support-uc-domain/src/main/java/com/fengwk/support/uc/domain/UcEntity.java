package com.fengwk.support.uc.domain;

import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.domain.model.Destroyable;

/**
 * 
 * @author fengwk
 */
public abstract class UcEntity extends BasicEntity<Long> implements Destroyable<Long> {

    protected UcEntity() {
        super(null);
    }

}
