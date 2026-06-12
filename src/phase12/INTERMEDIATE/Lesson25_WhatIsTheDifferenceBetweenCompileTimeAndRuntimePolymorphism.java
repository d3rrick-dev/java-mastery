package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between compile-time and runtime polymorphism?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson25_WhatIsTheDifferenceBetweenCompileTimeAndRuntimePolymorphism {
    public static void main(String[] args) {
        System.out.println("=== COMPILE-TIME VS RUNTIME POLYMORPHISM ===\n");
        System.out.println("Compile-time Polymorphism (Static):");
        System.out.println("  - Method overloading");
        System.out.println("  - Resolved at compile time");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Faster execution");
        System.out.println();
        System.out.println("Runtime Polymorphism (Dynamic):");
        System.out.println("  - Method overriding");
        System.out.println("  - Resolved at runtime");
        System.out.println("  - Same method name, same parameters");
        System.out.println("  - Uses @Override annotation");
        System.out.println("  - Slower due to dynamic dispatch");
    }
}
