package phase7;

import java.util.concurrent.*;

/**
 * LESSON 7: THREAD POOLS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A thread pool is a collection of pre-created threads that wait for tasks.
 * Instead of creating a new thread for each task, you reuse existing threads.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Thread creation is expensive (~1ms per thread)
 * - Thread pools reuse threads (amortize creation cost)
 * - Limits maximum concurrent threads (prevents resource exhaustion)
 * - Provides better response time (threads already exist)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant with 5 chefs:
 * - Without pool: Hire new chef for each order (expensive, slow)
 * - With pool: 5 chefs ready, orders assigned to available chefs
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API Gateway handling 10,000 requests/second:
 * - Without pool: Create 10,000 threads (OOM crash)
 * - With pool: 200 threads handle all requests (efficient)
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is a thread pool and why use it?"
 * Answer: Reusable threads that reduce creation overhead and
 *         limit resource usage
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using unbounded queue with unbounded thread creation
 * - Not shutting down thread pools (memory leak)
 * - Wrong pool size (too small = bottleneck, too large = overhead)
 * - Ignoring RejectedExecutionException
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Pool size too small: Tasks wait in queue
 * - Pool size too large: Context switching overhead
 * - Optimal: CPU-bound = cores, I/O-bound = cores * (1 + wait/compute)
 *
 * ============================================================
 * THREAD POOL TYPES
 * ============================================================
 *
 *   FixedThreadPool       CachedThreadPool      ScheduledThreadPool
 *   +----------------+    +----------------+    +----------------+
 *   | Core = Max     |    | Core = 0       |    | Core = Max     |
 *   | Queue: unbounded|    | Max: unbounded |    | Queue: Delayed |
 *   +----------------+    +----------------+    +----------------+
 *   | Use: CPU-bound |    | Use: Many short|    | Use: Scheduled |
 *   |                 |    | tasks          |    | tasks          |
 *   +----------------+    +----------------+    +----------------+
 *
 *   WorkStealingPool (Java 8+)
 *   +----------------+
 *   | Work-stealing  |
 *   | ForkJoinPool   |
 *   +----------------+
 *   | Use: Parallel  |
 *   | recursive tasks|
 *   +----------------+
 */

public class Lesson07_ThreadPools {

    public static void main(String[] args) throws Exception {
        System.out.println("=== THREAD POOLS ===\n");

        // ============================================================
        // EXAMPLE 1: Fixed Thread Pool
        // ============================================================
        System.out.println("--- Example 1: Fixed Thread Pool ---\n");

        ExecutorService fixedPool = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            fixedPool.execute(() -> {
                System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        fixedPool.shutdown();
        fixedPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Fixed pool completed\n");

        // ============================================================
        // EXAMPLE 2: Cached Thread Pool
        // ============================================================
        System.out.println("--- Example 2: Cached Thread Pool ---\n");

        ExecutorService cachedPool = Executors.newCachedThreadPool();

        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            cachedPool.execute(() -> {
                System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        cachedPool.shutdown();
        cachedPool.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Cached pool completed\n");

        // ============================================================
        // EXAMPLE 3: Single Thread Executor
        // ============================================================
        System.out.println("--- Example 3: Single Thread Executor ---\n");

        ExecutorService singlePool = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            singlePool.execute(() -> {
                System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
            });
        }

        singlePool.shutdown();
        singlePool.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Single pool completed\n");

        // ============================================================
        // EXAMPLE 4: Scheduled Thread Pool
        // ============================================================
        System.out.println("--- Example 4: Scheduled Thread Pool ---\n");

        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);

        // Schedule task with delay
        scheduledPool.schedule(() -> {
            System.out.println("Delayed task executed");
        }, 1, TimeUnit.SECONDS);

        // Schedule recurring task
        ScheduledFuture<?> future = scheduledPool.scheduleAtFixedRate(() -> {
            System.out.println("Recurring task at " + System.currentTimeMillis());
        }, 0, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(2500);
        future.cancel(false);
        scheduledPool.shutdown();
        System.out.println("Scheduled pool completed\n");

        // ============================================================
        // EXAMPLE 5: Custom Thread Pool Configuration
        // ============================================================
        System.out.println("--- Example 5: Custom Thread Pool ---\n");

        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
            2,                          // corePoolSize
            4,                          // maximumPoolSize
            60, TimeUnit.SECONDS,       // keepAliveTime
            new ArrayBlockingQueue<>(2)  // workQueue
        );

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            try {
                customPool.execute(() -> {
                    System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " rejected (pool full)");
            }
        }

        customPool.shutdown();
        customPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Custom pool completed\n");

        // ============================================================
        // EXAMPLE 6: Thread Pool Monitoring
        // ============================================================
        System.out.println("--- Example 6: Thread Pool Monitoring ---\n");

        ThreadPoolExecutor monitorPool = new ThreadPoolExecutor(
            2, 4, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2)
        );

        System.out.println("Initial pool size: " + monitorPool.getPoolSize());
        System.out.println("Core size: " + monitorPool.getCorePoolSize());
        System.out.println("Max size: " + monitorPool.getMaximumPoolSize());
        System.out.println("Queue size: " + monitorPool.getQueue().size());

        // Submit tasks
        for (int i = 0; i < 3; i++) {
            monitorPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(200);
        System.out.println("After submitting tasks:");
        System.out.println("Pool size: " + monitorPool.getPoolSize());
        System.out.println("Active threads: " + monitorPool.getActiveCount());
        System.out.println("Queue size: " + monitorPool.getQueue().size());
        System.out.println("Completed tasks: " + monitorPool.getCompletedTaskCount());

        monitorPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When to use which pool
        // ============================================================
        System.out.println("--- Example 7: Pool Selection Guide ---\n");

        System.out.println("FixedThreadPool:");
        System.out.println("  - CPU-bound tasks");
        System.out.println("  - Known, stable load");
        System.out.println("  - Size = number of CPU cores");
        System.out.println();
        System.out.println("CachedThreadPool:");
        System.out.println("  - Many short-lived tasks");
        System.out.println("  - Unpredictable load");
        System.out.println("  - Tasks complete quickly");
        System.out.println();
        System.out.println("SingleThreadExecutor:");
        System.out.println("  - Tasks must execute sequentially");
        System.out.println("  - Order matters");
        System.out.println("  - No concurrent execution needed");
        System.out.println();
        System.out.println("ScheduledThreadPool:");
        System.out.println("  - Periodic tasks");
        System.out.println("  - Delayed execution");
        System.out.println("  - Cron-like scheduling");
    }
}
