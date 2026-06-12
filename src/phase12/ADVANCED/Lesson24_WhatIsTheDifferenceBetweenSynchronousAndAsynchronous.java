package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between synchronous and asynchronous?
 *
 * Difficulty: ADVANCED
 */

public class Lesson24_WhatIsTheDifferenceBetweenSynchronousAndAsynchronous {
    public static void main(String[] args) {
        System.out.println("=== SYNCHRONOUS VS ASYNCHRONOUS ===\n");
        System.out.println("Synchronous:");
        System.out.println("  - Tasks execute one after another");
        System.out.println("  - Caller waits for operation to complete");
        System.out.println("  - Blocking: thread waits for result");
        System.out.println("  - Example: Traditional method calls");
        System.out.println("  - Flow: A -> B -> C (sequential)");
        System.out.println();
        System.out.println("Asynchronous:");
        System.out.println("  - Tasks can execute independently");
        System.out.println("  - Caller doesn't wait for operation to complete");
        System.out.println("  - Non-blocking: thread continues execution");
        System.out.println("  - Example: Callbacks, CompletableFuture, reactive");
        System.out.println("  - Flow: A -> B -> C (parallel/concurrent)");
        System.out.println();
        System.out.println("Real-World Example:");
        System.out.println("  - Synchronous: Restaurant - order, wait, eat, pay");
        System.out.println("  - Asynchronous: Restaurant - order, get buzzer, sit, eat when buzzed");
        System.out.println();
        System.out.println("Java Examples:");
        System.out.println("  - Synchronous: future.get()");
        System.out.println("  - Asynchronous: CompletableFuture.thenApply()");
    }
}
