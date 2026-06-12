package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between overloading and overriding?
 *
 * Difficulty: ADVANCED
 */

public class Lesson05_WhatIsTheDifferenceBetweenOverloadingAndOverriding {
    public static void main(String[] args) {
        System.out.println("=== OVERLOADING VS OVERRIDING ===\n");
        System.out.println("Overloading (Compile-time Polymorphism):");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Happens within the same class");
        System.out.println("  - Return type can be different");
        System.out.println("  - Example: print(int), print(String), print(int, int)");
        System.out.println();
        System.out.println("Overriding (Runtime Polymorphism):");
        System.out.println("  - Same method signature in parent and child class");
        System.out.println("  - Happens across inheritance hierarchy");
        System.out.println("  - Return type must be compatible");
        System.out.println("  - Example: Animal.speak() overridden by Dog.speak()");
        System.out.println();
        System.out.println("Key Rules for Overriding:");
        System.out.println("  - Method signature must match exactly");
        System.out.println("  - Cannot reduce visibility (public -> private not allowed)");
        System.out.println("  - Cannot throw broader checked exceptions");
        System.out.println("  - Use @Override annotation to catch errors");
    }
}
