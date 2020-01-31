package com.fengwk.support.core.util;

/**
 * 
 * @author fengwk
 */
public class Ref<T> {

    public final T value;
    
    private Ref(T value) {
        this.value = value;
    }
    
    public static <T> Ref<T> of(T value) {
        return new Ref<>(value);
    }
    
}
