package com.fengwk.support.spring.boot.starter.mysql.convention;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.fengwk.support.core.bean.Property;
import com.fengwk.support.domain.model.BasicEntity;
import com.fengwk.support.spring.boot.starter.mysql.BasicPO;

/**
 * 
 * @author fengwk
 */
public class PropertyMapper {

    private final Map<Property<?, ?>, Property<?, ?>> sourceToTargetMap = new ConcurrentHashMap<>();
    
    public synchronized void register(Property<? extends BasicEntity<?>, ?> source, Property<? extends BasicPO<?>, ?> target) {
        sourceToTargetMap.put(source, target);
    }
    
    @SuppressWarnings("unchecked")
    public <R> Property<?, R> getTarget(Property<?, R> source) {
        Property<?, R> target = (Property<?, R>) sourceToTargetMap.get(Objects.requireNonNull(source));
        if (target == null) {
            throw new IllegalArgumentException("Cannot mapping property " + source.toString() + ".");
        }
        return target;
    }
    
}