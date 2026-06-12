package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between constructor and method?
 *
 * Difficulty: BEGINNER
 */

public class Lesson47_WhatIsTheDifferenceBetweenConstructorAndMethod {
    public static void main(String[] args) {
        System.out.println("=== CONSTRUCTOR VS METHOD ===\n");
        System.out.println("Constructor:");
        System.out.println("  - Initializes new objects");
        System.out.println("  - Same name as class");
        System.out.println("  - No return type (not even void)");
        System.out.println("  - Called automatically with new keyword");
        System.out.println("  - Cannot be inherited");
        System.out.println();
        System.out.println("Method:");
        System.out.println("  - Performs operations/actions");
        System.out.println("  - Any valid method name");
        System.out.println("  - Has return type (or void)");
        System.out.println("  - Called explicitly");
        System.out.println("  - Can be inherited and overridden");
    }
}
