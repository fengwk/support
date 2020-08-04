package com.fengwk.support.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.fengwk.support.core.convention.exception.Preconditions;
import lombok.Data;

/**
 * 受检对象
 * 
 * @author fengwk
 */
public abstract class Checked<T> {

    private static final Result SUCCESS = new Result(true, null);
    
    private final List<Checker<T>> checkers = new ArrayList<>();
    
    /**
     * impl in subclass
     */
    protected abstract void throwOnFailure(Result result, PrintableArg... args);
    
    public void checkAndThrowIfNecessary(T value) {
        checkAndThrowIfNecessary(value, (PrintableArg[]) null);
    }
    
    public void checkAndThrowIfNecessary(T value, Supplier<PrintableArg[]> argsSupplier) {
        Result result = check(value);
        if (!result.isSuccess()) {
            throwOnFailure(result, argsSupplier.get());
        }
    }
    
    private void checkAndThrowIfNecessary(T value, PrintableArg... args) {
        Result result = check(value);
        if (!result.isSuccess()) {
            throwOnFailure(result, args);
        }
    }
    
    public Result check(T value) {
        for (Checker<T> checker : checkers) {
            Result result = checker.apply(value);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return SUCCESS;
    }
    
    protected PrintableArg[] args(PrintableArg... args) {
        return args;
    }
    
    protected PrintableArg arg(String name, Object value) {
        return new PrintableArg(name, value);
    }
    
    protected void appendChecker(Checker<T> checker) {
        checkers.add(checker);
    }
    
    protected Result success() {
        return SUCCESS;
    }
    
    protected Result failure(String message) {
        return new Result(false, message);
    }
    
    @Data
    protected static class PrintableArg {
        
        private final String name;
        private final Object value;
        
        private PrintableArg(String name, Object value) {
            Preconditions.notBlank(name, "name cannot be blank.");
            Preconditions.notNull(value, "value cannot be null.");
            this.name = name;
            this.value = value;
        }
        
    }
    
    @Data
    protected static class Result {
        
        private final boolean success;
        private final String message;
        
        private Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

    }
    
    @FunctionalInterface
    protected interface Checker<T> extends Function<T, Result> {}
    
}
