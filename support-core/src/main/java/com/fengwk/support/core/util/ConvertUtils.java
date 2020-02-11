package com.fengwk.support.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 
 * @author fengwk
 */
public class ConvertUtils {

    public static final Integer DEFAULT_INT_FALSE = 0;
    public static final Integer DEFAULT_INT_TRUE = 1;
    
    private ConvertUtils() {}
    
    public static <T, R> R mapIfNotNull(T t, Function<T, R> mapper) {
        if (t == null) {
            return null;
        }
        return mapper.apply(t);
    }
    
    public static <T, R> List<R> mapToList(Collection<T> collection, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream().map(mapper).collect(Collectors.toList());
    }
    
    public static Integer boolToInt(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool ? DEFAULT_INT_TRUE : DEFAULT_INT_FALSE;
    }
    
    public static Boolean intToBool(Integer i) {
        if (i == null) {
            return null;
        } else if (Objects.equals(i, DEFAULT_INT_FALSE)) {
            return false;
        } else if (Objects.equals(i, DEFAULT_INT_TRUE)) {
            return true;
        } else {
            throw new IllegalArgumentException("Int must be zero or one.");
        }
    }
    
}
