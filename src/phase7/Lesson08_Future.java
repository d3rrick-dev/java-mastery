package phase7;

import java.util.concurrent.*;

/**
 * LESSON 8: FUTURE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Future represents the result of an asynchronous computation.
 * It's like a promise: "I'll give you the result later."
 * You can check if it's done, wait for it, or cancel it.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Allows non-blocking computation
 * - You can start a task and do other work while waiting
 * - Provides cancellation capability
 * - Enables timeout handling
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Food delivery app:
 * - Order food (submit task)
 * - Get Future (receipt with order number)
 * - Do other things while waiting
 * - Check Future.isDone() to see if ready
 * - Future.get() to pick up food
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Microservice calling another service:
 * - Service A calls Service B (async)
 * - Gets Future<Response>
 * - Processes other requests while waiting
 * - Future.get() when response needed
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is Future in Java?"
 * Answer: Represents result of async computation, provides
 *         isDone(), get(), cancel() methods
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Calling get() without timeout (can block forever)
 * - Not handling InterruptedException
 * - Forgetting to check isDone() before get()
 * - Not handling ExecutionException
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - get() blocks - use with timeout in production
 * - isDone() is non-blocking (cheap check)
 * - cancel() may not interrupt running task
 * - Multiple get() calls return same result (cached)
 *
 * ============================================================
 * FUTURE METHODS
 * ============================================================
 *
 *   +------------------+
 *   |      Future<V>   |
 *   +------------------+
 *   | get()            |  <-- Blocks until done
 *   | get(timeout, u)  |  <-- Blocks with timeout
 *   | isDone()         |  <-- Check if complete
 *   | isCancelled()    |  <-- Check if cancelled
 *   | cancel(mayInterrupt) | <-- Cancel task
 *   +------------------+
 */

public class Lesson08_Future {

    public static void main(String[] args) throws Exception {
        System.out.println("=== FUTURE ===\n");

        // ============================================================
        // EXAMPLE 1: Basic Future usage
        // ============================================================
        System.out.println("--- Example 1: Basic Future ---\n");

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            System.out.println("Task started...");
            Thread.sleep(1000);
            System.out.println("Task completed!");
            return 42;
        });

        // Do other work while waiting
        System.out.println("Doing other work...");
        Thread.sleep(500);
        System.out.println("Still doing other work...");

        // Get result (blocks if not done)
        System.out.println("Getting result...");
        Integer result = future.get();
        System.out.println("Result: " + result + "\n");

        executor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Future with timeout
        // ============================================================
        System.out.println("--- Example 2: Future with Timeout ---\n");

        ExecutorService timeoutExecutor = Executors.newSingleThreadExecutor();

        Future<Integer> timeoutFuture = timeoutExecutor.submit(() -> {
            Thread.sleep(3000);  // Takes 3 seconds
            return 100;
        });

        try {
            // Wait max 1 second
            Integer timeoutResult = timeoutFuture.get(1, TimeUnit.SECONDS);
            System.out.println("Result: " + timeoutResult);
        } catch (TimeoutException e) {
            System.out.println("Timeout! Task took too long");
            timeoutFuture.cancel(true);
        }

        timeoutExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Checking if done
        // ============================================================
        System.out.println("--- Example 3: isDone() Check ---\n");

        ExecutorService checkExecutor = Executors.newSingleThreadExecutor();

        Future<String> checkFuture = checkExecutor.submit(() -> {
            Thread.sleep(2000);
            return "Done!";
        });

        // Poll until done
        while (!checkFuture.isDone()) {
            System.out.println("Task not done yet...");
            Thread.sleep(300);
        }

        System.out.println("Task done! Result: " + checkFuture.get() + "\n");

        checkExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Cancelling a Future
        // ============================================================
        System.out.println("--- Example 4: Cancelling Future ---\n");

        ExecutorService cancelExecutor = Executors.newSingleThreadExecutor();

        Future<?> cancelFuture = cancelExecutor.submit(() -> {
            int count = 0;
            while (count < 10 && !Thread.currentThread().isInterrupted()) {
                System.out.println("Working... " + count);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
            }
        });

        Thread.sleep(1500);
        System.out.println("Cancelling task...");
        boolean cancelled = cancelFuture.cancel(true);

        System.out.println("Cancelled: " + cancelled);
        System.out.println("Is done: " + cancelFuture.isDone());
        System.out.println("Is cancelled: " + cancelFuture.isCancelled());

        cancelExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Multiple Futures
        // ============================================================
        System.out.println("--- Example 5: Multiple Futures ---\n");

        ExecutorService multiExecutor = Executors.newFixedThreadPool(3);

        Future<Integer> f1 = multiExecutor.submit(() -> {
            Thread.sleep(500);
            return 10;
        });

        Future<Integer> f2 = multiExecutor.submit(() -> {
            Thread.sleep(300);
            return 20;
        });

        Future<Integer> f3 = multiExecutor.submit(() -> {
            Thread.sleep(700);
            return 30;
        });

        // Wait for all
        int sum = f1.get() + f2.get() + f3.get();
        System.out.println("Sum: " + sum);

        multiExecutor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Future limitations
        // ============================================================
        System.out.println("--- Example 6: Future Limitations ---\n");

        System.out.println("Future limitations:");
        System.out.println("1. Cannot manually complete a Future");
        System.out.println("2. Cannot chain multiple Futures");
        System.out.println("3. Cannot combine multiple Futures");
        System.out.println("4. No exception handling without get()");
        System.out.println("5. get() blocks - no non-blocking alternative");
        System.out.println();
        System.out.println("Solution: Use CompletableFuture (Lesson 9)");
    }
}
