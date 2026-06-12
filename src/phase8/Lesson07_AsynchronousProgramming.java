package phase8;

import java.util.concurrent.*;

/**
 * LESSON 7: ASYNCHRONOUS PROGRAMMING
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Asynchronous programming allows you to start a task and continue
 * working without waiting for it to complete. Like ordering food at
 * a restaurant - you don't stand at the counter waiting, you get
 * a buzzer and do other things.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Improve throughput (do other work while waiting)
 * - Better resource utilization
 * - Responsive applications (UI doesn't freeze)
 * - Handle I/O-bound operations efficiently
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Food delivery app:
 * - Order food (async)
 * - Track delivery (async)
 * - Browse other restaurants (while waiting)
 * - Get notification when ready
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Microservice architecture:
 * - Service A calls Service B (async)
 * - Service A calls Service C (async)
 * - Combine results when both complete
 * - Much faster than sequential calls
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is asynchronous programming?"
 * Answer: Non-blocking execution, start task and continue,
 *         get result later via callback or future
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Blocking on async results (defeats the purpose)
 * - Not handling exceptions in callbacks
 * - Creating too many async tasks (resource exhaustion)
 * - Not understanding thread pools
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Async reduces waiting time
 * - Callback overhead is minimal
 * - Thread pool size affects throughput
 * - Too many tasks = queue buildup
 */

public class Lesson07_AsynchronousProgramming {

    public static void main(String[] args) throws Exception {
        System.out.println("=== ASYNCHRONOUS PROGRAMMING ===\n");

        // ============================================================
        // EXAMPLE 1: Callback pattern
        // ============================================================
        System.out.println("--- Example 1: Callback Pattern ---\n");

        AsyncService service = new AsyncService();

        System.out.println("Requesting data...");
        service.fetchData(42, result -> {
            System.out.println("Callback received: " + result);
        });

        System.out.println("Doing other work while waiting...");
        Thread.sleep(1000);
        System.out.println("Main thread done\n");

        // ============================================================
        // EXAMPLE 2: Future-based async
        // ============================================================
        System.out.println("--- Example 2: Future-Based Async ---\n");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<String> future = executor.submit(() -> {
            Thread.sleep(500);
            return "Async result";
        });

        System.out.println("Future submitted, doing other work...");
        Thread.sleep(300);
        System.out.println("Still working...");

        // Get result when needed
        String result = future.get();
        System.out.println("Got result: " + result + "\n");

        executor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: CompletableFuture async
        // ============================================================
        System.out.println("--- Example 3: CompletableFuture ---\n");

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "CompletableFuture result";
        });

        cf.thenAccept(result2 -> {
            System.out.println("Received: " + result2);
        });

        System.out.println("Main continues...");
        Thread.sleep(1000);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Multiple async operations
        // ============================================================
        System.out.println("--- Example 4: Multiple Async Operations ---\n");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Result 1";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Result 2";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Result 3";
        });

        // Wait for all
        CompletableFuture<Void> all = CompletableFuture.allOf(cf1, cf2, cf3);
        all.get();

        System.out.println("All completed: " + cf1.get() + ", " + cf2.get() + ", " + cf3.get());
        System.out.println("(Total time ~300ms, not 600ms)\n");

        // ============================================================
        // EXAMPLE 5: Async with error handling
        // ============================================================
        System.out.println("--- Example 5: Error Handling ---\n");

        CompletableFuture<String> errorCf = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Async error!");
        })
        .exceptionally(ex -> {
            System.out.println("Caught: " + ex.getMessage());
            return "Default value";
        });

        System.out.println("Result: " + errorCf.get() + "\n");

        // ============================================================
        // EXAMPLE 6: Async patterns comparison
        // ============================================================
        System.out.println("--- Example 6: Async Patterns ---\n");

        System.out.println("1. Callbacks:");
        System.out.println("   - Simple, but leads to callback hell");
        System.out.println("   - Hard to compose");
        System.out.println();
        System.out.println("2. Future:");
        System.out.println("   - Better than callbacks");
        System.out.println("   - But get() blocks");
        System.out.println();
        System.out.println("3. CompletableFuture:");
        System.out.println("   - Non-blocking composition");
        System.out.println("   - Functional style");
        System.out.println("   - Best for Java 8+");
        System.out.println();
        System.out.println("4. Reactive (RxJava, Project Reactor):");
        System.out.println("   - Stream-based");
        System.out.println("   - Backpressure support");
        System.out.println("   - More complex");
    }

    // ============================================================
    // HELPER CLASS
    // ============================================================

    static class AsyncService {
        public void fetchData(int id, Callback<String> callback) {
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Data for " + id;
            }).thenAccept(callback::onComplete);
        }
    }

    interface Callback<T> {
        void onComplete(T result);
    }
}
