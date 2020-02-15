package com.fengwk.support.core.domain.event;

import java.lang.reflect.Type;

import com.fengwk.support.core.util.reflect.GenericUtils;

/**
 * @author fengwk
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
