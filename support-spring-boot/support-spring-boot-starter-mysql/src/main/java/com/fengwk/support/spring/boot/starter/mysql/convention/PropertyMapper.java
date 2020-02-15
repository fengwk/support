package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.fengwk.support.core.domain.model.BasicEntity;
import com.fengwk.support.core.util.bean.Property;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

/**
 * 
 * @author fengwk
 */
public class PropertyMapper<E extends BasicEntity<?>, P extends BasicPO<?>> {

    private final Map<Property<E, ?>, Property<P, ?>> sourceToTargetMap = new ConcurrentHashMap<>();
    
    public synchronized void register(Property<E, ?> source, Property<P, ?> target) {
        sourceToTargetMap.put(source, target);
    }
    
    @SuppressWarnings("unchecked")
    public <R> Property<P, R> getTarget(Property<E, R> source) {
        Property<P, R> target = (Property<P, R>) sourceToTargetMap.get(Objects.requireNonNull(source));
        if (target == null) {
            throw new IllegalArgumentException("Cannot mapping property " + source.toString() + ".");
        }
        return target;
    }
    
}