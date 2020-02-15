package com.fengwk.support.core.domain.event;

/**
 * 异步订阅者
 * 
 * @author fengwk
 */
public interface AsyncDomainEventSubscriber<E extends DomainEvent> {

    void consume(E event);
    
}
