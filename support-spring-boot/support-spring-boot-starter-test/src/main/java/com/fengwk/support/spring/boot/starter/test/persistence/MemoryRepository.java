package com.fengwk.support.spring.boot.starter.test.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fengwk.support.core.exception.Preconditions;

/**
 * 
 * @author fengwk
 */
public class MemoryRepository<T> {

    final Set<T> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
    
    public void insert(T record) {
        Preconditions.notNull(record, "Record cannot be null.");
        set.add(record);
    }
    
    public void delete(T record) {
        Preconditions.notNull(record, "Record cannot be null.");
        set.remove(record);
    }
    
    public T findOne(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        List<T> result = set.stream().filter(predicate).limit(1).collect(Collectors.toList());
        return result.isEmpty() ? null : result.get(0);
    }
    
    public List<T> findAll() {
        return set.stream().collect(Collectors.toList());
    }
    
    public List<T> findAll(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        return set.stream().filter(predicate).collect(Collectors.toList());
    }
    
    public long count() {
        return set.size();
    }
    
    public long count(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        return set.stream().filter(predicate).collect(Collectors.counting());
    }
    
}
