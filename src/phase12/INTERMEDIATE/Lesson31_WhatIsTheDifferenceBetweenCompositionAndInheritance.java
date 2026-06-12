package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between composition and inheritance?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson31_WhatIsTheDifferenceBetweenCompositionAndInheritance {
    public static void main(String[] args) {
        System.out.println("=== COMPOSITION VS INHERITANCE ===\n");
        System.out.println("Composition:");
        System.out.println("  - HAS-A relationship");
        System.out.println("  - More flexible, runtime flexibility");
        System.out.println("  - Can change behavior at runtime");
        System.out.println("  - Example: Car HAS Engine (can swap engines)");
        System.out.println();
        System.out.println("Inheritance:");
        System.out.println("  - IS-A relationship");
        System.out.println("  - Less flexible, compile-time binding");
        System.out.println("  - Tightly coupled to parent class");
        System.out.println("  - Example: Car IS-A Vehicle");
        System.out.println();
        System.out.println("Prefer composition over inheritance (GoF principle)");
    }
}
