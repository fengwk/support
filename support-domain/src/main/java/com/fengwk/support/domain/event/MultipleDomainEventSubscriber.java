package com.fengwk.support.domain.event;

/**
 * @author fengwk
 */
public interface MultipleDomainEventSubscriber {

    void consume(DomainEvent event);
    
}
