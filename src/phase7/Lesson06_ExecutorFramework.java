package phase7;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LESSON 6: EXECUTOR FRAMEWORK
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * The Executor Framework is a Java API for managing thread pools.
 * Instead of creating threads manually, you submit tasks to an executor,
 * and it handles thread creation, management, and reuse.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Manual thread creation is expensive and error-prone
 * - Thread pools reuse threads (better performance)
 * - Provides lifecycle management (shutdown, awaitTermination)
 * - Separates task submission from execution mechanics
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant kitchen:
 * - Without executor: Hire a new chef for each order (expensive!)
 * - With executor: Have a fixed team of chefs, assign orders to them
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Web server handling HTTP requests:
 * - Without executor: Create new thread per request (runs out of memory)
 * - With executor: Fixed thread pool handles all requests efficiently
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is the Executor Framework?"
 * Answer: A framework for managing thread pools, separating task
 *         submission from execution
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Forgetting to shutdown executor (threads keep running)
 * - Using Executor directly instead of ExecutorService
 * - Not handling exceptions in tasks
 * - Creating unbounded thread pools (can cause OOM)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Thread reuse reduces creation overhead
 * - Proper pool size prevents resource exhaustion
 * - Too many threads = context switching overhead
 * - Too few threads = underutilization
 *
 * ============================================================
 * EXECUTOR FRAMEWORK ARCHITECTURE
 * ============================================================
 *
 *   +------------------+
 *   |   Executor       |  <-- Interface: execute(Runnable)
 *   +------------------+
 *            |
 *            v
 *   +------------------+
 *   | ExecutorService  |  <-- Interface: submit, shutdown, etc.
 *   +------------------+
 *            |
 *            v
 *   +------------------+
 *   | ThreadPoolExecutor|  <-- Core implementation
 *   +------------------+
 *            |
 *            v
 *   +------------------+
 *   |   Factories      |  <-- Executors.newFixedThreadPool(), etc.
 *   +------------------+
 */

public class Lesson06_ExecutorFramework {

    public static void main(String[] args) throws Exception {
        System.out.println("=== EXECUTOR FRAMEWORK ===\n");

        // ============================================================
        // EXAMPLE 1: Basic Executor usage
        // ============================================================
        System.out.println("--- Example 1: Basic Executor ---\n");

        Executor executor = new SimpleExecutor();

        executor.execute(() -> {
            System.out.println("Task 1 running in: " + Thread.currentThread().getName());
        });

        executor.execute(() -> {
            System.out.println("Task 2 running in: " + Thread.currentThread().getName());
        });

        Thread.sleep(500);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: ExecutorService with fixed thread pool
        // ============================================================
        System.out.println("--- Example 2: ExecutorService ---\n");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executorService.execute(() -> {
                System.out.println("Task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Proper shutdown
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
        System.out.println("All tasks completed\n");

        // ============================================================
        // EXAMPLE 3: ExecutorService with Callable
        // ============================================================
        System.out.println("--- Example 3: ExecutorService with Callable ---\n");

        ExecutorService callableExecutor = Executors.newFixedThreadPool(2);

        java.util.concurrent.Future<Integer> future1 = callableExecutor.submit(() -> {
            Thread.sleep(500);
            return 100;
        });

        java.util.concurrent.Future<Integer> future2 = callableExecutor.submit(() -> {
            Thread.sleep(300);
            return 200;
        });

        System.out.println("Future 1 result: " + future1.get());
        System.out.println("Future 2 result: " + future2.get());

        callableExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: invokeAll - wait for all tasks
        // ============================================================
        System.out.println("--- Example 4: invokeAll ---\n");

        ExecutorService invokeExecutor = Executors.newFixedThreadPool(2);

        java.util.List<Callable<Integer>> tasks = java.util.List.of(
            () -> { Thread.sleep(200); return 1; },
            () -> { Thread.sleep(100); return 2; },
            () -> { Thread.sleep(300); return 3; }
        );

        java.util.List<Future<Integer>> results = invokeExecutor.invokeAll(tasks);

        for (int i = 0; i < results.size(); i++) {
            System.out.println("Task " + (i + 1) + " result: " + results.get(i).get());
        }

        invokeExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: invokeAny - get first successful result
        // ============================================================
        System.out.println("--- Example 5: invokeAny ---\n");

        ExecutorService anyExecutor = Executors.newFixedThreadPool(3);

        java.util.List<Callable<String>> searchTasks = java.util.List.of(
            () -> { Thread.sleep(500); return "Server-1"; },
            () -> { Thread.sleep(200); return "Server-2"; },  // Fastest
            () -> { Thread.sleep(800); return "Server-3"; }
        );

        String fastestResult = anyExecutor.invokeAny(searchTasks);
        System.out.println("Fastest result: " + fastestResult);

        anyExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Different executor types
        // ============================================================
        System.out.println("--- Example 6: Executor Types ---\n");

        System.out.println("1. newSingleThreadExecutor(): Single thread, sequential execution");
        System.out.println("2. newFixedThreadPool(n): Fixed number of threads");
        System.out.println("3. newCachedThreadPool(): Creates threads as needed, reuses idle");
        System.out.println("4. newScheduledThreadPool(n): For scheduled/delayed tasks");
        System.out.println("5. newWorkStealingPool(): ForkJoinPool (Java 8+)");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Graceful shutdown
        // ============================================================
        System.out.println("--- Example 7: Graceful Shutdown ---\n");

        ExecutorService service = Executors.newFixedThreadPool(2);

        // Submit long-running task
        service.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Task interrupted during shutdown");
                Thread.currentThread().interrupt();
            }
        });

        // Initiate shutdown
        System.out.println("Initiating shutdown...");
        service.shutdown();  // No new tasks

        // Wait for completion with timeout
        if (service.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS)) {
            System.out.println("Tasks completed normally");
        } else {
            System.out.println("Timeout! Forcing shutdown...");
            service.shutdownNow();
        }
    }

    /**
     * Simple Executor implementation.
     */
    static class SimpleExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }
}
