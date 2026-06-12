package phase7;

import java.util.concurrent.*;

/**
 * LESSON 9: COMPLETABLEFUTURE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * CompletableFuture is a Future that can be manually completed,
 * and supports functional-style callbacks and composition.
 * It's like Future on steroids - you can chain operations,
 * combine results, and handle errors elegantly.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Future is limited (no chaining, no manual completion)
 * - Need non-blocking composition of async operations
 * - Need better exception handling
 * - Need functional programming style for async code
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * E-commerce checkout:
 * 1. Get user data (async)
 * 2. Get cart items (async)
 * 3. Get shipping options (async)
 * 4. Combine all and calculate total
 * 5. Process payment
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API Gateway:
 * - Fetch user profile
 * - Fetch user preferences
 * - Fetch recommendations
 * - Combine and return personalized response
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is CompletableFuture?"
 * Answer: A Future that supports callbacks, chaining,
 *         and functional composition
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using get() (blocks) when thenApply() is available
 * - Not handling exceptions (exceptionally() or handle())
 * - Creating CompletableFuture without executor (uses common pool)
 * - Blocking in callbacks (defeats the purpose)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Non-blocking callbacks are more efficient than get()
 * - Custom executor prevents common pool exhaustion
 * - thenApplyAsync uses separate thread (overhead)
 * - thenApply runs in same thread (faster but may block)
 *
 * ============================================================
 * COMPLETABLEFUTURE PIPELINE
 * ============================================================
 *
 *   CompletableFuture
 *         |
 *         | thenApply()
 *         v
 *   CompletableFuture
 *         |
 *         | thenCompose()
 *         v
 *   CompletableFuture
 *         |
 *         | thenCombine()
 *         v
 *   CompletableFuture
 *         |
 *         | exceptionally()
 *         v
 *   Final Result
 */

public class Lesson09_CompletableFuture {

    public static void main(String[] args) throws Exception {
        System.out.println("=== COMPLETABLEFUTURE ===\n");

        // ============================================================
        // EXAMPLE 1: Basic CompletableFuture
        // ============================================================
        System.out.println("--- Example 1: Basic CompletableFuture ---\n");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Computing...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Result";
        });

        future.thenAccept(result -> {
            System.out.println("Got result: " + result);
        });

        // Wait for completion
        future.get();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Chaining with thenApply
        // ============================================================
        System.out.println("--- Example 2: Chaining with thenApply ---\n");

        CompletableFuture<Integer> chainFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1: Getting number");
            return 10;
        })
        .thenApply(num -> {
            System.out.println("Step 2: Doubling");
            return num * 2;
        })
        .thenApply(num -> {
            System.out.println("Step 3: Adding 5");
            return num + 5;
        });

        System.out.println("Final result: " + chainFuture.get() + "\n");

        // ============================================================
        // EXAMPLE 3: Async chaining
        // ============================================================
        System.out.println("--- Example 3: Async Chaining ---\n");

        CompletableFuture<String> asyncFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 in: " + Thread.currentThread().getName());
            return "Hello";
        })
        .thenApplyAsync(msg -> {
            System.out.println("Task 2 in: " + Thread.currentThread().getName());
            return msg + " World";
        })
        .thenApplyAsync(msg -> {
            System.out.println("Task 3 in: " + Thread.currentThread().getName());
            return msg + "!";
        });

        System.out.println("Result: " + asyncFuture.get() + "\n");

        // ============================================================
        // EXAMPLE 4: Combining futures with thenCombine
        // ============================================================
        System.out.println("--- Example 4: Combining Futures ---\n");

        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return 10;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return 20;
        });

        CompletableFuture<Integer> combined = f1.thenCombine(f2, (a, b) -> {
            System.out.println("Combining " + a + " + " + b);
            return a + b;
        });

        System.out.println("Combined result: " + combined.get() + "\n");

        // ============================================================
        // EXAMPLE 5: Exception handling
        // ============================================================
        System.out.println("--- Example 5: Exception Handling ---\n");

        CompletableFuture<String> errorFuture = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random error!");
            }
            return "Success";
        })
        .exceptionally(ex -> {
            System.out.println("Caught error: " + ex.getMessage());
            return "Default value";
        });

        System.out.println("Result: " + errorFuture.get() + "\n");

        // ============================================================
        // EXAMPLE 6: handle() - handle both success and failure
        // ============================================================
        System.out.println("--- Example 6: handle() Method ---\n");

        CompletableFuture<String> handleFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Error in supply");
        })
        .handle((result, ex) -> {
            if (ex != null) {
                return "Recovered from: " + ex.getMessage();
            }
            return result;
        });

        System.out.println("Handle result: " + handleFuture.get() + "\n");

        // ============================================================
        // EXAMPLE 7: thenCompose for dependent async operations
        // ============================================================
        System.out.println("--- Example 7: thenCompose ---\n");

        CompletableFuture<String> composed = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user ID");
            return "user-123";
        })
        .thenCompose(userId -> {
            System.out.println("Fetching user data for: " + userId);
            return CompletableFuture.supplyAsync(() -> {
                return "UserData{id='" + userId + "'}";
            });
        });

        System.out.println("Composed result: " + composed.get() + "\n");

        // ============================================================
        // EXAMPLE 8: allOf - wait for all futures
        // ============================================================
        System.out.println("--- Example 8: allOf ---\n");

        CompletableFuture<String> fA = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "A";
        });

        CompletableFuture<String> fB = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "B";
        });

        CompletableFuture<String> fC = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "C";
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(fA, fB, fC);
        all.get();  // Wait for all

        System.out.println("All completed: " + fA.get() + fB.get() + fC.get() + "\n");

        // ============================================================
        // EXAMPLE 9: anyOf - get first completed
        // ============================================================
        System.out.println("--- Example 9: anyOf ---\n");

        CompletableFuture<String> fast1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Slow";
        });

        CompletableFuture<String> fast2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Fast";
        });

        CompletableFuture<Object> any = CompletableFuture.anyOf(fast1, fast2);
        System.out.println("First completed: " + any.get() + "\n");

        // ============================================================
        // EXAMPLE 10: Manual completion
        // ============================================================
        System.out.println("--- Example 10: Manual Completion ---\n");

        CompletableFuture<String> manualFuture = new CompletableFuture<>();

        // Complete from another thread
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            manualFuture.complete("Manually completed!");
        }).start();

        System.out.println("Waiting for manual completion...");
        System.out.println("Result: " + manualFuture.get() + "\n");

        // ============================================================
        // EXAMPLE 11: Custom executor
        // ============================================================
        System.out.println("--- Example 11: Custom Executor ---\n");

        Executor customExecutor = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("Custom executor: " + Thread.currentThread().getName());
            return "Done";
        }, customExecutor)
        .thenAccept(result -> System.out.println("Result: " + result));

        Thread.sleep(500);
        ((ExecutorService) customExecutor).shutdown();
    }
}
