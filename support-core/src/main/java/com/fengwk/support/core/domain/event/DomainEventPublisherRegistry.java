package com.fengwk.support.core.domain.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author fengwk
 */
public class DomainEventPublisherRegistry {

    private static final Map<Type, DomainEventPublisher<? extends DomainEventSubscriber<? extends DomainEvent>, ? extends DomainEvent>> DYNAMIC_MAP = new ConcurrentHashMap<>();
    
    public static <S extends DomainEventSubscriber<E>, E extends DomainEvent> DomainEventPublisher<S, E> get(Class<E> eventType) {
        return get((Type) eventType);
    }
    
    static <S extends DomainEventSubscriber<E>, E extends DomainEvent> DomainEventPublisher<S, E> get(Type eventType) {
        if (eventType == null) {
            throw new NullPointerException("EventType cannot be null.");
        }
        if (eventType instanceof Class) {
            if (!DomainEvent.class.isAssignableFrom((Class<?>) eventType)) {
                throw new IllegalArgumentException("EventType[" + eventType + "] error.");
            }
        } else if (eventType instanceof ParameterizedType) {
            if (!DomainEvent.class.isAssignableFrom((Class<?>) ((ParameterizedType) eventType).getRawType())) {
                throw new IllegalArgumentException("EventType[" + eventType + "] error.");
            }
        } else {
            throw new IllegalArgumentException("EventType[" + eventType + "] error.");
        }
        @SuppressWarnings("unchecked")
        DomainEventPublisher<S, E> publisher = (DomainEventPublisher<S, E>) DYNAMIC_MAP.computeIfAbsent(eventType, k -> new ConcurrentDomainEventPublisher<>());
        return publisher;
    }
    
    static <S extends DomainEventSubscriber<E>, E extends DomainEvent> void subscribe(Type eventType, S subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber cannot be null.");
        }
        if (eventType == null) {
            throw new NullPointerException("EventType cannot be null.");
        }
        DomainEventPublisherRegistry.<S,E>get(eventType).subscribe(subscriber);
    }
    
}
