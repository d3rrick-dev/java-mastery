package phase8;

import java.util.concurrent.*;

/**
 * LESSON 8: COMPLETABLEFUTURE PIPELINES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * CompletableFuture pipelines chain multiple async operations
 * together. Each step depends on the previous one, forming a
 * pipeline of transformations.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Compose async operations elegantly
 * - Avoid callback hell
 * - Handle errors at each stage
 * - Non-blocking composition
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * E-commerce checkout:
 * 1. Get user (async)
 * 2. Get cart (async, depends on user)
 * 3. Get shipping (async, depends on cart)
 * 4. Calculate total (depends on all above)
 * 5. Process payment (depends on total)
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API Gateway:
 * 1. Authenticate request
 * 2. Fetch user profile
 * 3. Fetch permissions
 * 4. Validate access
 * 5. Forward to service
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How do you chain async operations?"
 * Answer: thenApply, thenCompose, thenCombine
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using get() in pipeline (blocks)
 * - Not handling exceptions
 * - Creating nested CompletableFutures incorrectly
 * - Blocking in callbacks
 */

public class Lesson08_CompletableFuturePipelines {

    public static void main(String[] args) throws Exception {
        System.out.println("=== COMPLETABLEFUTURE PIPELINES ===\n");

        // ============================================================
        // EXAMPLE 1: Simple pipeline
        // ============================================================
        System.out.println("--- Example 1: Simple Pipeline ---\n");

        CompletableFuture<String> pipeline = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1: Fetching user");
            return "user-123";
        })
        .thenApply(userId -> {
            System.out.println("Step 2: Fetching profile for " + userId);
            return "Profile{name='Alice'}";
        })
        .thenApply(profile -> {
            System.out.println("Step 3: Enriching " + profile);
            return profile + ", email=alice@example.com";
        });

        System.out.println("Result: " + pipeline.get() + "\n");

        // ============================================================
        // EXAMPLE 2: thenCompose for dependent async
        // ============================================================
        System.out.println("--- Example 2: thenCompose ---\n");

        CompletableFuture<String> composed = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user ID");
            return "user-123";
        })
        .thenCompose(userId -> fetchProfileAsync(userId))
        .thenCompose(profile -> fetchOrdersAsync(profile))
        .thenApply(orders -> {
            System.out.println("Processing " + orders.size() + " orders");
            return "Done";
        });

        System.out.println("Composed result: " + composed.get() + "\n");

        // ============================================================
        // EXAMPLE 3: thenCombine for parallel results
        // ============================================================
        System.out.println("--- Example 3: thenCombine ---\n");

        CompletableFuture<Integer> userAge = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return 30;
        });

        CompletableFuture<Integer> userScore = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return 95;
        });

        CompletableFuture<String> combined = userAge.thenCombine(userScore, (age, score) -> {
            return "Age: " + age + ", Score: " + score;
        });

        System.out.println("Combined: " + combined.get() + "\n");

        // ============================================================
        // EXAMPLE 4: Error handling in pipeline
        // ============================================================
        System.out.println("--- Example 4: Error Handling ---\n");

        CompletableFuture<String> withError = CompletableFuture.<String>supplyAsync(() -> {
            throw new RuntimeException("Network error");
        })
        .thenApply(result -> result.toUpperCase())
        .exceptionally(ex -> {
            System.out.println("Error: " + ex.getMessage());
            return "DEFAULT";
        })
        .thenApply(result -> result + "!");

        System.out.println("Result: " + withError.get() + "\n");

        // ============================================================
        // EXAMPLE 5: handle() for both success and failure
        // ============================================================
        System.out.println("--- Example 5: handle() ---\n");

        CompletableFuture<String> handled = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random failure");
            }
            return "Success";
        })
        .handle((result, ex) -> {
            if (ex != null) {
                return "Recovered from: " + ex.getCause().getMessage();
            }
            return result;
        });

        System.out.println("Handled: " + handled.get() + "\n");

        // ============================================================
        // EXAMPLE 6: Real-world pipeline
        // ============================================================
        System.out.println("--- Example 6: Real-World Pipeline ---\n");

        CompletableFuture<String> orderPipeline = CompletableFuture.supplyAsync(() -> {
            System.out.println("1. Validating order");
            return "order-456";
        })
        .thenCompose(Lesson08_CompletableFuturePipelines::validateOrderAsync)
        .thenCompose(Lesson08_CompletableFuturePipelines::fetchInventoryAsync)
        .thenCompose(Lesson08_CompletableFuturePipelines::processPaymentAsync)
        .thenApply(receipt -> {
            System.out.println("5. Order complete: " + receipt);
            return receipt;
        });

        System.out.println("Pipeline result: " + orderPipeline.get() + "\n");

        // ============================================================
        // EXAMPLE 7: Pipeline best practices
        // ============================================================
        System.out.println("--- Example 7: Best Practices ---\n");

        System.out.println("1. Use thenCompose for async dependencies");
        System.out.println("2. Use thenApply for sync transformations");
        System.out.println("3. Use thenCombine for parallel results");
        System.out.println("4. Always handle exceptions (exceptionally/handle)");
        System.out.println("5. Use custom executor to avoid common pool exhaustion");
        System.out.println("6. Don't block in callbacks");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    static CompletableFuture<String> fetchProfileAsync(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Profile{userId='" + userId + "'}";
        });
    }

    static CompletableFuture<String> fetchOrdersAsync(String profile) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Orders for " + profile;
        });
    }

    static CompletableFuture<String> validateOrderAsync(String orderId) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Validated: " + orderId;
        });
    }

    static CompletableFuture<String> fetchInventoryAsync(String order) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return order + " + Inventory";
        });
    }

    static CompletableFuture<String> processPaymentAsync(String inventory) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "Receipt: " + inventory;
        });
    }
}
