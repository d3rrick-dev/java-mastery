package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between Executor and ExecutorService?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson10_WhatIsTheDifferenceBetweenExecutorAndExecutorService {
    public static void main(String[] args) {
        System.out.println("=== EXECUTOR VS EXECUTORSERVICE ===\n");
        System.out.println("Executor:");
        System.out.println("  - Interface with single method: execute(Runnable)");
        System.out.println("  - Fire-and-forget execution");
        System.out.println("  - No way to get result or track completion");
        System.out.println("  - No shutdown method");
        System.out.println();
        System.out.println("ExecutorService:");
        System.out.println("  - Extends Executor interface");
        System.out.println("  - submit() returns Future");
        System.out.println("  - invokeAll(), invokeAny() for batch tasks");
        System.out.println("  - shutdown(), shutdownNow() for lifecycle");
        System.out.println("  - awaitTermination() for graceful shutdown");
        System.out.println();
        System.out.println("Use Executor for simple execution");
        System.out.println("Use ExecutorService for managed thread pools");
    }
}
