package com.fengwk.support.core.util;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fengwk.support.core.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author fengwk
 */
@Slf4j
public class ThreadPool {
    
    final ThreadPoolExecutor instance;

    private ThreadPool(ThreadPoolExecutor instance) {
        this.instance = instance;
    }
    
    final static ThreadPool DEFAULT;
    
    final static ThreadPool IO;
    
    static {
        DEFAULT = new ThreadPool(new ThreadPoolExecutor(
                Constants.AVAILABLE_PROCESSORS + 1,
                Constants.AVAILABLE_PROCESSORS * 4,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
        
        IO = new ThreadPool(new ThreadPoolExecutor(
                Constants.AVAILABLE_PROCESSORS * 4,
                Constants.AVAILABLE_PROCESSORS * 8,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DEFAULT.instance.shutdown();
            IO.instance.shutdown();
            
            try {
                // 等待运行完毕再终止程序
                while (!DEFAULT.instance.awaitTermination(1, TimeUnit.SECONDS));
                while (!IO.instance.awaitTermination(1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }));
    }
    
    public static ThreadPool instance() {
        return DEFAULT;
    }
    
    public static ThreadPool io() {
        return IO;
    }
    
    public void execute(Runnable command) {
        instance.execute(command);
    }
    
    public Future<?> submit(Runnable task) {
        return instance.submit(task);
    }
    
    public <T> Future<T> submit(Runnable task, T result) {
        return instance.submit(task, result);
    }
    
    public <T> Future<T> submit(Callable<T> callable) {
        return instance.submit(callable);
    }
    
}
