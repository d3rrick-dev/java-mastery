package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between Callable and Runnable?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson09_WhatIsTheDifferenceBetweenCallableAndRunnable {
    public static void main(String[] args) {
        System.out.println("=== CALLABLE VS RUNNABLE ===\n");
        System.out.println("Runnable:");
        System.out.println("  - Functional interface with run() method");
        System.out.println("  - Returns void");
        System.out.println("  - Cannot throw checked exceptions");
        System.out.println("  - Used with Thread or ExecutorService");
        System.out.println();
        System.out.println("Callable:");
        System.out.println("  - Functional interface with call() method");
        System.out.println("  - Returns a value (generic type V)");
        System.out.println("  - Can throw checked exceptions");
        System.out.println("  - Returns Future<V> when submitted");
        System.out.println();
        System.out.println("Use Runnable for fire-and-forget tasks");
        System.out.println("Use Callable when you need a result or exception handling");
    }
}
