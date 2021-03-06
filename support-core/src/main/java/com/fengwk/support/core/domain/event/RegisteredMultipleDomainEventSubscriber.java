package com.fengwk.support.core.domain.event;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author fengwk
 */
public abstract class RegisteredMultipleDomainEventSubscriber implements MultipleDomainEventSubscriber {

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

        @Override
        public int getOrder() {
            return RegisteredMultipleDomainEventSubscriber.this.getOrder();
        }
        
    }

}
