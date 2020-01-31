package com.fengwk.support.core.domain.event;

/**
 * 
 * @author fengwk
 */
public interface DomainEventSubscriber<E extends DomainEvent> extends Ordered {

    void consume(E event);
    
}
