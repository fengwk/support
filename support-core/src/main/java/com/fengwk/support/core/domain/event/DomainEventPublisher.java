package com.fengwk.support.core.domain.event;

/**
 * 
 * @author fengwk
 */
public interface DomainEventPublisher<S extends DomainEventSubscriber<E>, E extends DomainEvent> {

    void publish(E event);
    
    void subscribe(S subscriber);
    
}
