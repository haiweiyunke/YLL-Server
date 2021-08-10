package yll.component.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.github.relucent.base.util.thread.ProcessWorker;

@Component
public class ExecutorManager {
    // ==============================Fields===========================================
    /** ThreadPool */
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    // ==============================Methods==========================================
    /**
     * 执行任务
     * @param task 任务
     */
    public void execute(Runnable task) {
        threadPool.execute(task);
    }

    /**
     * 创建并执行[生产-消费]工作任务
     * @param name 任务名称
     * @param supplier 任务生产者
     * @param consumer 任务消费者
     * @return 工作任务
     */
    public <T> ProcessWorker<T> executeWorker(String name, Supplier<T> supplier, Consumer<T> consumer) {
        ProcessWorker<T> worker = new ProcessWorker<T>(name, supplier, consumer);
        execute(worker);
        return worker;
    }

    /**
     * 创建并行的任务消费者
     * @param consumer 消费者
     * @param size 并发数(1~20)
     * @return 并行的任务消费者
     */
    public <T> Consumer<T> parallel(Consumer<T> consumer, int size) {
        return new ParallelConsumer<>(consumer, size, threadPool);
    }

    /** 应用结束时调用方法 */
    @PreDestroy
    private void destroy() {
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            threadPool.shutdownNow();
        }
    }
}
