package com.fengwk.support.core.bean;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author fengwk
 */
public class Property<T, R> {
    
    private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
    private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

    private final Property<?, T> parent;
    private final SerializedLambda desc;
    
    volatile Class<?> originalBeanClass;
    volatile Class<T> beanClass;
    volatile String name;
    volatile String path;
    
    private Property(Property<?, T> parent, Fn<T, R> fn) {
        this.parent = parent;
        try {
            this.desc = parseToSerializedLambda(fn);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    private SerializedLambda parseToSerializedLambda(Fn<T, R> fn) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = fn.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        return (SerializedLambda) method.invoke(fn);
    }
    
    public static <T, R> Property<T, R> of(Fn<T, R> fn) {
        return new Property<>(null, fn);
    }
    
    public <U> Property<R, U> turn(Fn<R, U> fn) {
        return new Property<>(this, fn);
    }
    
    public Class<?> getOriginalBeanClass() {
        if (this.originalBeanClass != null) {
            return this.originalBeanClass;
        }
        synchronized (this) {
            if (this.originalBeanClass != null) {
                return this.originalBeanClass;
            }
            evalOriginalBeanClass();
        }
        return this.originalBeanClass;
    }
    
    public Class<T> getBeanClass() {
        if (this.beanClass != null) {
            return this.beanClass;
        }
        synchronized (this) {
            if (this.beanClass != null) {
                return this.beanClass;
            }
            evalBeanClass();
        }
        return this.beanClass;
    }
    
    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        synchronized (this) {
            if (this.name != null) {
                return this.name;
            }
            evalName();
        }
        return this.name;
    }
    
    public String getPath() {
        if (this.path != null) {
            return this.path;
        }
        synchronized (this) {
            if (this.path != null) {
                return this.path;
            }
            evalPath();
        }
        return this.path;
    }
    
    public void evalOriginalBeanClass() {
        Property<?, ?> p = this;
        while (p.parent != null) {
            p = p.parent;
        }
        this.originalBeanClass = p.getBeanClass();
    }
    
    @SuppressWarnings("unchecked")
    private void evalBeanClass() {
        String classPath = this.desc.getImplClass();
        try {
            this.beanClass = (Class<T>) Class.forName(classPath.replace('/', '.'));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
    
    private void evalName() {
        String getter = this.desc.getImplMethodName();
        if (GET_PATTERN.matcher(getter).matches()) {
            getter = getter.substring(3);
        } else if (IS_PATTERN.matcher(getter).matches()) {
            getter = getter.substring(2);
        }
        this.name = Introspector.decapitalize(getter);
    }
    
    private void evalPath() {
        LinkedList<Property<?, ?>> stack = new LinkedList<>();
        Property<?, ?> p = this;
        while (p != null) {
            stack.push(p);
            p = p.parent;
        }
        StringJoiner joiner = new StringJoiner(".");
        while (!stack.isEmpty()) {
            joiner.add(stack.pop().getName());
        }
        this.path = joiner.toString();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getOriginalBeanClass())
                .append(getBeanClass())
                .append(getPath())
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        @SuppressWarnings("unchecked")
        Property<T, R> other = (Property<T, R>) obj;
        return new EqualsBuilder()
                .append(getOriginalBeanClass(), other.getOriginalBeanClass())
                .append(getBeanClass(), other.getBeanClass())
                .append(getPath(), other.getPath())
                .build();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getOriginalBeanClass())
                .append(getBeanClass())
                .append(getPath()).build();
    }

    @FunctionalInterface
    public interface Fn<T, R> extends Function<T, R>, Serializable {}
    
}
