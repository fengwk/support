package com.fengwk.support.spring.boot.starter.test.persistence;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.fengwk.support.core.domain.model.Identity;
import com.fengwk.support.core.util.gson.GsonUtils;

import lombok.Data;

/**
 * 
 * @author fengwk
 */
public class MemoryDB {

    static final Map<Class<?>, MemoryRepository<?>> repoMap = new ConcurrentHashMap<>();
    
    static final Map<Class<?>, IdentityMemoryRepository<? extends Identity<?>, ?>> identityRepoMap = new ConcurrentHashMap<>();
    
    @SuppressWarnings("unchecked")
    public static <T> MemoryRepository<T> repo(Class<T> clazz) {
        return (MemoryRepository<T>) repoMap.computeIfAbsent(clazz, key -> new MemoryRepository<>());
    }
    
    @SuppressWarnings("unchecked")
    public static <T, I> IdentityMemoryRepository<? extends Identity<T>, I> identityRepo(Class<T> clazz) {
        return (IdentityMemoryRepository<? extends Identity<T>, I>) identityRepoMap.computeIfAbsent(clazz, key -> new IdentityMemoryRepository<>());
    }
    
    public static String view() {
        List<TableView> views = repoMap.entrySet().stream()
                .map(entry -> {
                    Class<?> clazz = entry.getKey();
                    MemoryRepository<?> repo = entry.getValue();
                    List<Object> records = repo.set.stream().collect(Collectors.toList());
                    return new TableView(clazz.getName(), records);
                })
                .collect(Collectors.toList());
        
        List<TableView> views2 = identityRepoMap.entrySet().stream()
                .map(entry -> {
                    Class<?> clazz = entry.getKey();
                    IdentityMemoryRepository<? extends Identity<?>, ?> repo = entry.getValue();
                    List<Object> records = repo.map.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
                    return new TableView(clazz.getName(), records);
                })
                .collect(Collectors.toList());
        
        views.addAll(views2);
        return GsonUtils.toJson(views);
    }
    
    public static void show() {
        System.out.println(view());
    }
    
    @Data
    static class TableView {
        
        final String name;
        final List<Object> records;
        
    }
    
}
