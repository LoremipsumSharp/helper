package io.github.loremipsumsharp;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public final class Task {
    
    private static final Object lock = new Object();

    private static ExecutorService fixedPool;

    public static ExecutorService getPool() {
        synchronized (lock) {
            if (fixedPool != null && !fixedPool.isShutdown())
                return fixedPool;
            var runtime = Runtime.getRuntime();
            var processors = runtime.availableProcessors();
            return fixedPool = Executors.newFixedThreadPool(processors);
        }
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<Future<T>> invokeAll(List<Callable<T>> tasks)
            throws InterruptedException {
        var executor = Task.getPool();
        return executor.invokeAll(tasks);
    }

    public static <T> List<T> whenAllInvoke(List<Callable<T>> tasks)
            throws InterruptedException {
        var future = invokeAll(tasks);
        var unpack = future.stream().map(f -> Task.get(f));
        return unpack.collect(Collectors.toList());
    }

    public static <T> List<T> whenAll(Collection<CompletableFuture<T>> tasks) {
        var array = tasks.toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(array).join();
        return tasks.stream().map(t -> get(t)).toList();
    }

    private static <T> T get(Future<T> task) {
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T await(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> CompletableFuture<T> run(Callable<Callable<T>> callable) {
        return CompletableFuture.supplyAsync(() -> await(await(callable)));
    }

    public static <T> T await(CompletableFuture<T> callable) {
        return Task.blockGet(callable);
    }

    private static <T> T blockGet(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> CompletableFuture<T> wrapBlock(Future<T> future) {
        try {
            return CompletableFuture.completedFuture(future.get());
        } catch (InterruptedException e) {
            return CompletableFuture.failedFuture(e);
        } catch (ExecutionException e) {
            return CompletableFuture.failedFuture(e.getCause());
        }
    }

    public static <T> CompletableFuture<T> wrap(Future<T> future) {
        return future instanceof CompletableFuture<T> cf ? cf : wrap(future::get);
    }

    public static <T> CompletableFuture<T> wrap(Callable<T> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
