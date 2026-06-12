package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between final, finally, and finalize?
 *
 * Difficulty: ADVANCED
 */

public class Lesson03_WhatIsTheDifferenceBetweenFinalFinallyAndFinalize {
    public static void main(String[] args) {
        System.out.println("=== FINAL, FINALLY, FINALIZE ===\n");
        System.out.println("final:");
        System.out.println("  - Keyword for variables, methods, classes");
        System.out.println("  - Variable: cannot be reassigned");
        System.out.println("  - Method: cannot be overridden");
        System.out.println("  - Class: cannot be extended");
        System.out.println();
        System.out.println("finally:");
        System.out.println("  - Block that always executes after try-catch");
        System.out.println("  - Used for cleanup (closing resources)");
        System.out.println("  - Executes even if exception occurs or return is called");
        System.out.println();
        System.out.println("finalize():");
        System.out.println("  - Method called by GC before object is collected");
        System.out.println("  - Deprecated in Java 9+");
        System.out.println("  - Not guaranteed to be called");
        System.out.println("  - Should NOT be relied upon for cleanup");
    }
}
