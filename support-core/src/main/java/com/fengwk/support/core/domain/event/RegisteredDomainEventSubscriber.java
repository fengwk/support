/*
 * Project: empi-core
 * 
 * File Created at 2019年12月28日
 * 
 * Copyright 2012 Greenline.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Greenline Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Greenline.com.
 */
package com.fengwk.support.core.domain.event;

import java.lang.reflect.Type;

import com.fengwk.support.core.reflect.GenericUtils;

/**
 * @author fengwk
 * @version V1.0
 * @since 2019-12-28 15:48
 */
public abstract class RegisteredDomainEventSubscriber<E extends DomainEvent> implements DomainEventSubscriber<E> {

    protected RegisteredDomainEventSubscriber() {
        init();
    }
    
    private void init() {
        DomainEventPublisherRegistry.subscribe(findEventTypeBy(this), this);
    }
    
    private static <S extends DomainEventSubscriber<E>, E extends DomainEvent> Type findEventTypeBy(S subscriber) {
        return GenericUtils.findNearestVarType(subscriber.getClass(), DomainEventSubscriber.class, 0);
    }

}
