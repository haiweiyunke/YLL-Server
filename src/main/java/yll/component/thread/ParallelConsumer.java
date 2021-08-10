package yll.component.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * 可并行执行的消费者类
 */
public class ParallelConsumer<T> implements Consumer<T> {

    private final Consumer<T> consumer;
    private final Semaphore semaphore;
    private final Executor executor;

    /**
     * 可并行执行的消费者类
     * @param consumer 原始消费者类
     * @param size 并发数(1~20)
     * @param executor 执行器 @see {@link Executors}
     */
    public ParallelConsumer(Consumer<T> consumer, int size, Executor executor) {
        this.consumer = consumer;
        this.semaphore = new Semaphore(Math.max(Math.min(20, size), 1), true);
        this.executor = executor;
    }

    @Override
    public void accept(T t) {
        try {
            semaphore.acquire();
            try {
                executor.execute(() -> consumer.accept(t));
            } finally {
                semaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
