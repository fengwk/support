package com.fengwk.support.spring.boot.starter.test.persistence;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fengwk.support.core.exception.Preconditions;
import com.fengwk.support.domain.model.Identity;

/**
 * 
 * @author fengwk
 */
public class IdentityMemoryRepository<T extends Identity<I>, I> {

    final Map<I, T> map = new ConcurrentHashMap<>();
    
    IdentityMemoryRepository() {}
    
    public void insert(T record) {
        Preconditions.notNull(record, "Record cannot be null.");
        map.put(record.getId(), record);
    }
    
    public void delete(I id) {
        Preconditions.notNull(id, "Id cannot be null.");
        map.remove(id);
    }
    
    public void update(T record) {
        Preconditions.notNull(record, "Record cannot be null.");
        Preconditions.notNull(record.getId(), "Record's id cannot be null.");
        map.put(record.getId(), record);
    }
    
    public T find(I id) {
        Preconditions.notNull(id, "Id cannot be null.");
        return map.get(id);
    }
    
    public T findOne(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        List<T> result = map.entrySet().stream().map(Entry::getValue).filter(predicate).limit(1).collect(Collectors.toList());
        return result.isEmpty() ? null : result.get(0);
    }
    
    public List<T> findAll() {
        return map.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
    }
    
    public List<T> findAll(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        return map.entrySet().stream().map(Entry::getValue).filter(predicate).collect(Collectors.toList());
    }
    
    public long count() {
        return map.size();
    }
    
    public long count(Predicate<T> predicate) {
        Preconditions.notNull(predicate, "Predicate cannot be null.");
        return map.entrySet().stream().map(Entry::getValue).filter(predicate).collect(Collectors.counting());
    }
    
}
