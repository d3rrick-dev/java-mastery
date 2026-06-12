package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between Future and CompletableFuture?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson08_WhatIsTheDifferenceBetweenFutureAndCompletableFuture {
    public static void main(String[] args) {
        System.out.println("=== FUTURE VS COMPLETABLEFUTURE ===\n");
        System.out.println("Future:");
        System.out.println("  - Represents result of async computation");
        System.out.println("  - get() blocks until result available");
        System.out.println("  - No manual completion");
        System.out.println("  - No chaining/composition");
        System.out.println("  - No exception handling");
        System.out.println();
        System.out.println("CompletableFuture:");
        System.out.println("  - Implements Future + CompletionStage");
        System.out.println("  - Non-blocking: thenApply, thenAccept, thenRun");
        System.out.println("  - Can complete manually: complete(), completeExceptionally()");
        System.out.println("  - Supports chaining and composition");
        System.out.println("  - Has exception handling: exceptionally(), handle()");
        System.out.println("  - Supports combining: thenCombine, thenCompose");
        System.out.println();
        System.out.println("Use Future for simple async, CompletableFuture for complex pipelines");
    }
}
