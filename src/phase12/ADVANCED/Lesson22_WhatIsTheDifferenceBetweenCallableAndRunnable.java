package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Callable and Runnable?
 *
 * Difficulty: ADVANCED
 */

public class Lesson22_WhatIsTheDifferenceBetweenCallableAndRunnable {
    public static void main(String[] args) {
        System.out.println("=== CALLABLE VS RUNNABLE ===\n");
        System.out.println("Runnable:");
        System.out.println("  - Functional interface with run() method");
        System.out.println("  - Returns void (no result)");
        System.out.println("  - Cannot throw checked exceptions");
        System.out.println("  - Used with Thread class or ExecutorService");
        System.out.println("  - Introduced in Java 1.0");
        System.out.println();
        System.out.println("Callable:");
        System.out.println("  - Generic interface with call() method");
        System.out.println("  - Returns a value (V)");
        System.out.println("  - Can throw checked exceptions");
        System.out.println("  - Used with ExecutorService (submit method)");
        System.out.println("  - Introduced in Java 5");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Return type: void vs generic V");
        System.out.println("  - Exceptions: cannot throw vs can throw checked");
        System.out.println("  - Result: no result vs Future<V> result");
        System.out.println("  - Usage: Thread vs ExecutorService");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - How do you get result from Callable?");
        System.out.println("  - Answer: Future.get() or CompletableFuture");
    }
}
