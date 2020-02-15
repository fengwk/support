package com.fengwk.support.core.convention.exception;

import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author fengwk
 */
public class Preconditions {
    
    private Preconditions() {}
    
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw createException(message);
        }
    }
    
    public static void notNullExists(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            return;
        }
        for (Object object : array) {
            notNull(object, message);
        }
    }
    
    public static void notNullExists(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }
        for (Object object : collection) {
            notNull(object, message);
        }
    }
    
    public static void notEmpty(long[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(int[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(short[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(char[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(byte[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(double[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(float[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(boolean[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(Object[] array, String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw createException(message);
        }
    }
    
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw createException(message);
        }
    }
    
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw createException(message);
        }
    }
    
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw createException(message);
        }
    }
    
    private static IllegalArgumentException createException(String message) {
        return new IllegalArgumentException(message);
    }
    
}
