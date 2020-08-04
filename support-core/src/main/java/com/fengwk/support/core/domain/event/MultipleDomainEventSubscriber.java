package com.fengwk.support.core.domain.event;

/**
 * @author fengwk
 */
public interface MultipleDomainEventSubscriber extends Ordered {

    void consume(DomainEvent event);
    
}
