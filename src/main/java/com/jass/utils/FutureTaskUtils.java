package com.jass.utils;

import java.util.concurrent.*;

public class FutureTaskUtils {

    public static <T> T invokeTask(Callable<T> task, int taskTimeout) {
        T result = null;

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<T> future = executor.submit(task);

        try {
            result = future.get(taskTimeout, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            future.cancel(true);
            executor.shutdown();
            if (!executor.isTerminated())
                executor.shutdownNow();
        }
        return result;
    }
}
