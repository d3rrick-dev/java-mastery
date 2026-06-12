package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between method overloading and method overriding?
 *
 * Difficulty: ADVANCED
 */

public class Lesson42_WhatIsTheDifferenceBetweenMethodOverloadingAndMethodOverriding {
    public static void main(String[] args) {
        System.out.println("=== METHOD OVERLOADING VS METHOD OVERRIDING ===\n");
        System.out.println("Method Overloading:");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Happens within the same class");
        System.out.println("  - Resolved at compile time (static binding)");
        System.out.println("  - Return type can be different");
        System.out.println("  - Example: print(int), print(String), print(int, int)");
        System.out.println();
        System.out.println("Method Overriding:");
        System.out.println("  - Same method signature in parent and child class");
        System.out.println("  - Happens across inheritance hierarchy");
        System.out.println("  - Resolved at runtime (dynamic binding)");
        System.out.println("  - Return type must be compatible (covariant return types allowed)");
        System.out.println("  - Example: Animal.speak() overridden by Dog.speak()");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Location: same class vs parent-child");
        System.out.println("  - Parameters: different vs same");
        System.out.println("  - Binding: compile-time vs runtime");
        System.out.println("  - Purpose: flexibility vs polymorphism");
    }
}
