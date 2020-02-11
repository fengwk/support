package com.fengwk.support.domain.event;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author fengwk
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
