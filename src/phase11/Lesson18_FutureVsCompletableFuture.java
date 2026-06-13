package phase11;

import java.util.concurrent.*;

/**
 * LESSON 18: FUTURE VS COMPLETABLEFUTURE
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Future limitations
 * 2. CompletableFuture features
 * 3. Pipeline composition
 * 4. Combining futures
 * 5. Interview questions
 */

public class Lesson18_FutureVsCompletableFuture {
    public static void main(String[] args) {
        System.out.println("""
            === FUTURE VS COMPLETABLEFUTURE ===
            
            1. FUTURE LIMITATIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Future is a basic async result holder with limited capabilities.
            
               WHY IT EXISTS:
               - Basic async result handling
               - Introduced in Java 5
            
               SIMPLE EXAMPLE:
                   Future<String> future = executor.submit(() -> {
                       return "result";
                   });
                   
                   // Limitations:
                   // - get(): Blocking, no timeout by default
                   // - Cannot manually complete
                   // - No callback/notification
                   // - Cannot chain operations
                   // - Cannot combine multiple futures
            
               REAL-WORLD BACKEND EXAMPLE:
                   A simple async task:
                   Future<User> userFuture = userService.findUserAsync(id);
                   User user = userFuture.get();  // Blocks thread
                   // No way to transform or combine
            
               INTERVIEW QUESTION:
                   "What are the limitations of Future?
                   Why was CompletableFuture introduced?"
            
               COMMON MISTAKES:
                   - Blocking on get()
                   - Not handling timeouts
            
            ─────────────────────────────────────────────────────────────────────
            
            2. COMPLETABLEFUTURE FEATURES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               CompletableFuture extends Future with composition and callbacks.
            
               WHY IT EXISTS:
               - Non-blocking composition
               - Functional async programming
            
               SIMPLE EXAMPLE:
                   CompletableFuture<String> cf = CompletableFuture
                       .supplyAsync(() -> "data")
                       .thenApply(String::toUpperCase)
                       .thenApply(s -> "Result: " + s)
                       .exceptionally(ex -> "Error: " + ex.getMessage());
                   
                   // Features:
                   // - thenApply(): Transform result
                   // - thenAccept(): Consume result
                   // - exceptionally(): Handle errors
                   // - complete(): Manually complete
                   // - allOf(): Wait for multiple
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data processing pipeline:
                   CompletableFuture<Order> order = fetchOrderAsync(id)
                       .thenApply(this::validate)
                       .thenApply(this::process)
                       .thenAccept(this::save)
                       .exceptionally(this::handleError);
            
               INTERVIEW QUESTION:
                   "What is CompletableFuture?
                   How does it enable functional async programming?"
            
               COMMON MISTAKES:
                   - Not handling exceptions
                   - Blocking on get()
            
            ─────────────────────────────────────────────────────────────────────
            
            3. PIPELINE COMPOSITION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Chain operations that execute asynchronously.
            
               WHY IT EXISTS:
               - Declarative async code
               - Avoid callback hell
            
               SIMPLE EXAMPLE:
                   CompletableFuture.supplyAsync(() -> {
                           return "raw data";
                       })
                       .thenApply(data -> data.toUpperCase())
                       .thenApply(processed -> "Result: " + processed)
                       .thenAccept(result -> System.out.println(result))
                       .exceptionally(ex -> {
                           System.out.println("Error: " + ex.getMessage());
                           return null;
                       });
                   
                   // Each stage runs when previous completes
                   // Default: ForkJoinPool.commonPool()
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservice call chain:
                   CompletableFuture<User> user = fetchUserProfile(userId)
                       .thenCompose(profile -> fetchOrders(profile))
                       .thenApply(orders -> enrich(orders))
                       .thenAccept(this::cache);
            
               INTERVIEW QUESTION:
                   "How do CompletableFuture pipelines work?
                   What is the difference between thenApply and thenCompose?"
            
               COMMON MISTAKES:
                   - Not understanding async execution
                   - Blocking in pipeline
            
            ─────────────────────────────────────────────────────────────────────
            
            4. COMBINING FUTURES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Combine multiple async operations.
            
               WHY IT EXISTS:
               - Parallel execution
               - Aggregation
            
               SIMPLE EXAMPLE:
                   // Combine two futures:
                   CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello");
                   CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World");
                   
                   CompletableFuture<String> combined = cf1.thenCombine(cf2, 
                       (s1, s2) -> s1 + " " + s2
                   );
                   
                   // Wait for all:
                   CompletableFuture.allOf(cf1, cf2)
                       .thenRun(() -> System.out.println("Both done"));
                   
                   // Wait for first:
                   CompletableFuture.anyOf(cf1, cf2)
                       .thenAccept(result -> System.out.println("First: " + result));
            
               REAL-WORLD BACKEND EXAMPLE:
                   A fan-out/fan-in pattern:
                   CompletableFuture<User> user = fetchUserAsync(id);
                   CompletableFuture<List<Order>> orders = fetchOrdersAsync(id);
                   CompletableFuture<Profile> profile = fetchProfileAsync(id);
                   
                   CompletableFuture<UserData> combined = user
                       .thenCombine(orders, this::withOrders)
                       .thenCombine(profile, this::withProfile);
            
               INTERVIEW QUESTION:
                   "How to combine multiple CompletableFuture?
                   What is the difference between allOf and anyOf?"
            
               COMMON MISTAKES:
                   - Not handling all futures
                   - Exception in one future
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            CompletableFuture is essential for:
            - Modern async programming
            - Non-blocking I/O
            - Microservices orchestration
            - Reactive programming
            """);
    }
}
