package com.fengwk.support.core.domain.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fengwk.support.core.util.ThreadPool;

/**
 * 
 * @author fengwk
 */
class ConcurrentDomainEventPublisher<S extends DomainEventSubscriber<E>, E extends DomainEvent> implements DomainEventPublisher<S, E> {

    private volatile List<S> subscribers = Collections.emptyList();
    
    ConcurrentDomainEventPublisher() {}
    
    @Override
    public void publish(E event) {
        List<S> subscribers = this.subscribers;
        for (S subscriber : subscribers) {
            if (subscriber instanceof AsyncDomainEventSubscriber) {
                ThreadPool.instance().execute(() -> subscriber.consume(event));
            } else if (subscriber instanceof AsyncMultipleDomainEventSubscriber) {
                ThreadPool.instance().execute(() -> subscriber.consume(event));
            } else {
                subscriber.consume(event);
            }
        }
    }

    @Override
    public void subscribe(S subscriber) {
        List<S> subscribers = new ArrayList<>(this.subscribers);
        subscribers.add(subscriber);
        Collections.sort(subscribers, (s1, s2) -> Integer.compare(s1.getOrder(), s2.getOrder()));
        this.subscribers = Collections.unmodifiableList(subscribers);
    }
    
}
