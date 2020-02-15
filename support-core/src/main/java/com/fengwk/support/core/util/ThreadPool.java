package com.fengwk.support.core.util;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fengwk.support.core.Constants;

/**
 * 
 * @author fengwk
 */
public class ThreadPool {

    private ThreadPool() {}
    
    private static final ThreadPoolExecutor INSTANCE;
    
    static {
        INSTANCE = new ThreadPoolExecutor(
                Constants.AVAILABLE_PROCESSORS * 2,
                Constants.AVAILABLE_PROCESSORS * 8,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            INSTANCE.shutdown();
        }));
    }
    
    public static void execute(Runnable command) {
        INSTANCE.execute(command);
    }
    
    public static Future<?> submit(Runnable task) {
        return INSTANCE.submit(task);
    }
    
    public static <T> Future<T> submit(Runnable task, T result) {
        return INSTANCE.submit(task, result);
    }
    
    public static <T> Future<T> submit(Callable<T> callable) {
        return INSTANCE.submit(callable);
    }
    
}
