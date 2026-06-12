package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Future and CompletableFuture?
 *
 * Difficulty: ADVANCED
 */

public class Lesson23_WhatIsTheDifferenceBetweenFutureAndCompletableFuture {
    public static void main(String[] args) {
        System.out.println("=== FUTURE VS COMPLETABLEFUTURE ===\n");
        System.out.println("Future:");
        System.out.println("  - Introduced in Java 5");
        System.out.println("  - Represents result of async computation");
        System.out.println("  - Methods: get(), cancel(), isDone(), isCancelled()");
        System.out.println("  - get() blocks until result available");
        System.out.println("  - Cannot be manually completed");
        System.out.println("  - No callback support");
        System.out.println("  - Cannot combine multiple Futures easily");
        System.out.println();
        System.out.println("CompletableFuture:");
        System.out.println("  - Introduced in Java 8");
        System.out.println("  - Implements Future and CompletionStage");
        System.out.println("  - Non-blocking: thenApply(), thenAccept(), thenRun()");
        System.out.println("  - Can be manually completed: complete()");
        System.out.println("  - Supports callbacks and chaining");
        System.out.println("  - Can combine: thenCombine(), thenCompose()");
        System.out.println("  - Supports exception handling: exceptionally(), handle()");
        System.out.println();
        System.out.println("Key Advantage:");
        System.out.println("  - CompletableFuture enables reactive/non-blocking programming");
        System.out.println("  - Future is blocking, CompletableFuture is non-blocking");
    }
}
