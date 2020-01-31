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

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author fengwk
 * @version V1.0
 * @since 2019-12-28 15:48
 */
public abstract class RegisteredMultipleDomainEventSubscriber<E extends DomainEvent> implements MultipleDomainEventSubscriber {

    protected RegisteredMultipleDomainEventSubscriber(@SuppressWarnings("unchecked") Class<? extends DomainEvent>... domainEventClasses) {
        init(domainEventClasses);
    }
    
    private void init(@SuppressWarnings("unchecked") Class<? extends DomainEvent>... domainEventClasses) {
        if (ArrayUtils.isNotEmpty(domainEventClasses)) {
            for (Class<? extends DomainEvent> domainEventClass : domainEventClasses) {
                DomainEventPublisherRegistry.subscribe(domainEventClass, new DomainEventSubscriberProxy());
            }
        }
    }
    
    private class DomainEventSubscriberProxy implements DomainEventSubscriber<DomainEvent> {

        @Override
        public void consume(DomainEvent event) {
            RegisteredMultipleDomainEventSubscriber.this.consume(event);
        }
        
    }

}
