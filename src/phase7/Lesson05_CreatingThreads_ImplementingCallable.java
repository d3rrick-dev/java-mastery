package phase7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * LESSON 5: CREATING THREADS - IMPLEMENTING CALLABLE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Callable is like Runnable, but it can RETURN a value and THROW exceptions.
 * It's a functional interface with a single method: V call() throws Exception.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Runnable cannot return values (void run())
 * - Runnable cannot throw checked exceptions
 * - Callable solves both problems
 * - Used with Future to get results asynchronously
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Data processing service:
 * - ProcessDataTask implements Callable<Result>
 * - Returns processed data
 * - Can throw ProcessingException if data is invalid
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Database query executor:
 * - QueryTask implements Callable<QueryResult>
 * - Returns query results
 * - Throws SQLException if query fails
 * - Executed by thread pool
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What's the difference between Runnable and Callable?"
 * Answer: Callable returns a value, can throw checked exceptions,
 *         used with Future
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Forgetting to handle ExecutionException
 * - Not handling InterruptedException from get()
 * - Blocking on get() without timeout (can hang forever)
 * - Using Callable directly (must wrap in FutureTask or use Executor)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Callable has same overhead as Runnable
 * - Future.get() blocks - use get(timeout, unit) to avoid hanging
 * - FutureTask combines Callable + Future in one object
 *
 * ============================================================
 * RUNNABLE vs CALLABLE vs FUTURE
 * ============================================================
 *
 *   RUNNABLE              CALLABLE               FUTURE
 *   +-------------+       +-------------+        +-------------+
 *   | run()       |       | call()      |        | get()       |
 *   | : void      |       | : V         |        | : V         |
 *   |             |       | throws Ex   |        |             |
 *   +-------------+       +-------------+        +-------------+
 *   | No return   |       | Returns val |        | Async result|
 *   | No throws   |       | Can throw   |        | Can cancel  |
 *   +-------------+       +-------------+        +-------------+
 *          |                      |                      |
 *          +----------+-----------+                      |
 *                     |                                  |
 *                     v                                  v
 *              FutureTask<V>  <------------------- Future<V>
 *              (implements both)
 */

public class Lesson05_CreatingThreads_ImplementingCallable {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CREATING THREADS: IMPLEMENTING CALLABLE ===\n");

        // ============================================================
        // EXAMPLE 1: Basic Callable with FutureTask
        // ============================================================
        System.out.println("--- Example 1: Basic Callable ---\n");

        Callable<String> task = () -> {
            System.out.println("Task executing in: " + Thread.currentThread().getName());
            Thread.sleep(500);
            return "Result from Callable";
        };

        FutureTask<String> futureTask = new FutureTask<>(task);
        Thread thread = new Thread(futureTask);
        thread.start();

        // Get result (blocks until complete)
        String result = futureTask.get();
        System.out.println("Result: " + result + "\n");

        // ============================================================
        // EXAMPLE 2: Callable with return value
        // ============================================================
        System.out.println("--- Example 2: Callable Returning Value ---\n");

        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum;
        };

        FutureTask<Integer> sumFuture = new FutureTask<>(sumTask);
        new Thread(sumFuture).start();

        Integer sum = sumFuture.get();
        System.out.println("Sum of 1-100: " + sum + "\n");

        // ============================================================
        // EXAMPLE 3: Callable with exception handling
        // ============================================================
        System.out.println("--- Example 3: Callable with Exceptions ---\n");

        Callable<Integer> riskyTask = () -> {
            System.out.println("Executing risky task...");
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random failure!");
            }
            return 42;
        };

        FutureTask<Integer> riskyFuture = new FutureTask<>(riskyTask);
        new Thread(riskyFuture).start();

        try {
            Integer result2 = riskyFuture.get();
            System.out.println("Result: " + result2);
        } catch (ExecutionException e) {
            System.out.println("Task failed with: " + e.getCause().getMessage());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Callable with timeout
        // ============================================================
        System.out.println("--- Example 4: Callable with Timeout ---\n");

        Callable<String> slowTask = () -> {
            Thread.sleep(3000);  // Takes 3 seconds
            return "Slow result";
        };

        FutureTask<String> slowFuture = new FutureTask<>(slowTask);
        new Thread(slowFuture).start();

        try {
            // Wait max 1 second
            String slowResult = slowFuture.get(1, java.util.concurrent.TimeUnit.SECONDS);
            System.out.println("Result: " + slowResult);
        } catch (java.util.concurrent.TimeoutException e) {
            System.out.println("Task timed out! Cancelling...");
            slowFuture.cancel(true);
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Multiple Callables with ExecutorService
        // ============================================================
        System.out.println("--- Example 5: Multiple Callables ---\n");

        java.util.concurrent.ExecutorService executor =
            java.util.concurrent.Executors.newFixedThreadPool(3);

        java.util.List<Future<Integer>> futures = new java.util.ArrayList<>();

        // Submit multiple tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Callable<Integer> calcTask = () -> {
                Thread.sleep(200);
                return taskId * taskId;
            };
            futures.add(executor.submit(calcTask));
        }

        // Collect results
        for (int i = 0; i < futures.size(); i++) {
            Integer result3 = futures.get(i).get();
            System.out.println("Task " + (i + 1) + " result: " + result3);
        }

        executor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Callable vs Runnable comparison
        // ============================================================
        System.out.println("--- Example 6: Callable vs Runnable ---\n");

        System.out.println("Runnable:");
        System.out.println("  - void run()");
        System.out.println("  - Cannot return value");
        System.out.println("  - Cannot throw checked exceptions");
        System.out.println("  - Used with Thread or Executor");
        System.out.println();
        System.out.println("Callable:");
        System.out.println("  - V call() throws Exception");
        System.out.println("  - Returns value of type V");
        System.out.println("  - Can throw checked exceptions");
        System.out.println("  - Used with Future/FutureTask");
        System.out.println();
        System.out.println("When to use which:");
        System.out.println("  - Runnable: Fire-and-forget tasks");
        System.out.println("  - Callable: Tasks that need to return results");
    }
}
