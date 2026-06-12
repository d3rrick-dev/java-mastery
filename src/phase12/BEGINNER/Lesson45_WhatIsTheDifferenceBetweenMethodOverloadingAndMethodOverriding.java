package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between method overloading and method overriding?
 *
 * Difficulty: BEGINNER
 */

public class Lesson45_WhatIsTheDifferenceBetweenMethodOverloadingAndMethodOverriding {
    public static void main(String[] args) {
        System.out.println("=== METHOD OVERLOADING VS OVERRIDING ===\n");
        System.out.println("Method Overloading:");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Same class");
        System.out.println("  - Compile-time polymorphism");
        System.out.println("  - Return type can differ");
        System.out.println();
        System.out.println("Method Overriding:");
        System.out.println("  - Same method name, same parameters");
        System.out.println("  - Parent-child classes");
        System.out.println("  - Runtime polymorphism");
        System.out.println("  - Return type must be same or covariant");
        System.out.println();
        System.out.println("@Override annotation helps catch errors");
    }
}
