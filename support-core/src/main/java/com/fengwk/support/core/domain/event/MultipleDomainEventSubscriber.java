package com.fengwk.support.core.domain.event;

/**
 * @author fengwk
 */
public interface MultipleDomainEventSubscriber {

    void consume(DomainEvent event);
    
}
