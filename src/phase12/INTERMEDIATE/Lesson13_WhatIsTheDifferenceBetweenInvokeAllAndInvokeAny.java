package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between invokeAll() and invokeAny()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson13_WhatIsTheDifferenceBetweenInvokeAllAndInvokeAny {
    public static void main(String[] args) {
        System.out.println("=== INVOKEALL() VS INVOKEANY() ===\n");
        System.out.println("invokeAll():");
        System.out.println("  - Executes all tasks");
        System.out.println("  - Returns list of Futures (one per task)");
        System.out.println("  - Waits for ALL tasks to complete");
        System.out.println("  - Returns results in same order as input");
        System.out.println();
        System.out.println("invokeAny():");
        System.out.println("  - Executes all tasks");
        System.out.println("  - Returns result of first successful task");
        System.out.println("  - Cancels remaining tasks after first success");
        System.out.println("  - Throws exception if all fail");
        System.out.println();
        System.out.println("Use invokeAll() when you need all results");
        System.out.println("Use invokeAny() when you need first successful result");
    }
}
