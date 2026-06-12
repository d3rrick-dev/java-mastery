package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between overloading and overriding?
 *
 * Difficulty: BEGINNER
 */

public class Lesson32_WhatIsTheDifferenceBetweenOverloadingAndOverriding {
    public static void main(String[] args) {
        System.out.println("=== OVERLOADING VS OVERRIDING ===\n");
        System.out.println("Overloading (Compile-time polymorphism):");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Same class");
        System.out.println("  - Resolved at compile time");
        System.out.println();
        System.out.println("Overriding (Runtime polymorphism):");
        System.out.println("  - Same method name, same parameters");
        System.out.println("  - Parent-child classes");
        System.out.println("  - Resolved at runtime");
        System.out.println();
        System.out.println("@Override annotation recommended for overriding");
    }
}
