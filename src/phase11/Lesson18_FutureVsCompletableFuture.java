package phase11;

import java.util.concurrent.*;

/**
 * LESSON 18: FUTURE VS COMPLETABLEFUTURE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Future: Represents result of async computation (Java 5)
 * CompletableFuture: Future with completion support (Java 8)
 * Like a receipt (Future) vs a smart tracker (CompletableFuture).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Future: Basic async result holder
 * - CompletableFuture: Composition, callbacks, pipelines
 */

public class Lesson18_FutureVsCompletableFuture {

    public static void main(String[] args) {
        System.out.println("=== FUTURE VS COMPLETABLEFUTURE ===\n");

        // ============================================================
        // EXAMPLE 1: Future limitations
        // ============================================================
        System.out.println("--- Example 1: Future Limitations ---\n");

        System.out.println("Future interface:");
        System.out.println("  - get(): Blocking, no timeout by default");
        System.out.println("  - isDone(): Check completion");
        System.out.println("  - isCancelled(): Check cancellation");
        System.out.println("  - cancel(): Attempt cancellation");
        System.out.println();
        System.out.println("Limitations:");
        System.out.println("  - Cannot manually complete");
        System.out.println("  - No callback/notification");
        System.out.println("  - Cannot chain operations");
        System.out.println("  - Cannot combine multiple futures");
        System.out.println("  - No exception handling");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: CompletableFuture features
        // ============================================================
        System.out.println("--- Example 2: CompletableFuture Features ---\n");

        System.out.println("CompletableFuture features:");
        System.out.println("  - thenApply(): Transform result");
        System.out.println("  - thenAccept(): Consume result");
        System.out.println("  - thenRun(): Run after completion");
        System.out.println("  - exceptionally(): Handle errors");
        System.out.println("  - allOf(): Wait for multiple futures");
        System.out.println("  - anyOf(): Wait for first future");
        System.out.println("  - complete(): Manually complete");
        System.out.println("  - completeExceptionally(): Complete with error");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: CompletableFuture pipeline
        // ============================================================
        System.out.println("--- Example 3: Pipeline Example ---\n");

        CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching data...");
            return "raw data";
        })
        .thenApply(data -> {
            System.out.println("Processing: " + data);
            return data.toUpperCase();
        })
        .thenApply(processed -> {
            System.out.println("Formatting: " + processed);
            return "Result: " + processed;
        })
        .thenAccept(result -> {
            System.out.println("Final: " + result);
        })
        .exceptionally(ex -> {
            System.out.println("Error: " + ex.getMessage());
            return null;
        });

        // Wait for completion
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Combining futures
        // ============================================================
        System.out.println("--- Example 4: Combining Futures ---\n");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<String> combined = cf1.thenCombine(cf2, (s1, s2) -> s1 + " " + s2);

        combined.thenAccept(result -> {
            System.out.println("Combined: " + result);
        });

        try { Thread.sleep(100); } catch (InterruptedException e) {}
        System.out.println();
    }

    // ============================================================
    // FUTURE VS COMPLETABLEFUTURE DETAILS
    // ============================================================
    /*
     * Future (java.util.concurrent.Future):
     * - Introduced in Java 5
     * - Represents async computation result
     * - Methods: get(), isDone(), cancel()
     * - Blocking get() is main limitation
     *
     * CompletableFuture (java.util.concurrent.CompletableFuture):
     * - Introduced in Java 8
     * - Implements Future + CompletionStage
     * - Non-blocking composition
     * - 50+ methods for async programming
     *
     * Key Methods:
     * - supplyAsync(): Async supplier
     * - thenApply(): Transform (map)
     * - thenAccept(): Consume (forEach)
     * - thenRun(): Execute (side effect)
     * - thenCombine(): Combine two futures
     * - allOf(): Wait for all
     * - anyOf(): Wait for first
     * - exceptionally(): Error handling
     * - handle(): Handle result or error
     */
}
