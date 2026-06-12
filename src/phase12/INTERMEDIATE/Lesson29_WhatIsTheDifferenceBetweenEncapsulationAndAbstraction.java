package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between encapsulation and abstraction?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson29_WhatIsTheDifferenceBetweenEncapsulationAndAbstraction {
    public static void main(String[] args) {
        System.out.println("=== ENCAPSULATION VS ABSTRACTION ===\n");
        System.out.println("Encapsulation:");
        System.out.println("  - Hiding internal details using access modifiers");
        System.out.println("  - Data hiding + getters/setters");
        System.out.println("  - Protects data from unauthorized access");
        System.out.println("  - Example: private fields with public methods");
        System.out.println();
        System.out.println("Abstraction:");
        System.out.println("  - Hiding implementation details, showing only functionality");
        System.out.println("  - Focus on WHAT, not HOW");
        System.out.println("  - Achieved via abstract classes/interfaces");
        System.out.println("  - Example: interface with method signatures, no implementation");
        System.out.println();
        System.out.println("Encapsulation: data hiding");
        System.out.println("Abstraction: implementation hiding");
    }
}
