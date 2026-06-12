package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between final, finally, and finalize?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson40_WhatIsTheDifferenceBetweenFinalFinallyAndFinalize {
    public static void main(String[] args) {
        System.out.println("=== FINAL VS FINALLY VS FINALIZE ===\n");
        System.out.println("final:");
        System.out.println("  - Keyword for constants, non-inheritance, non-overriding");
        System.out.println("  - final class: Cannot be extended");
        System.out.println("  - final method: Cannot be overridden");
        System.out.println("  - final variable: Cannot be reassigned");
        System.out.println();
        System.out.println("finally:");
        System.out.println("  - Block for cleanup code");
        System.out.println("  - Always executes (except System.exit())");
        System.out.println("  - Used with try-catch");
        System.out.println();
        System.out.println("finalize():");
        System.out.println("  - Method called by GC before object removal");
        System.out.println("  - Deprecated in Java 9+");
        System.out.println("  - Not guaranteed to be called");
    }
}
