package com.sbmatch.androtool.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ExecutorUtils {
    static final String TAG = "ExecutorUtils";
    private static final ExecutorService executor = Executors.newFixedThreadPool(64);

    public static java.util.concurrent.Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    public static <T> void runAsync(Callable<T> mainTask, Consumer<T> callback) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return mainTask.call();
            } catch (Exception e) {
                return null; // 异常返回null
            }
        }, executor)
                .thenAccept(result -> {
                    // 无论成功失败都执行回调，调用方自己判断null
                    callback.accept(result);
                });
    }
}