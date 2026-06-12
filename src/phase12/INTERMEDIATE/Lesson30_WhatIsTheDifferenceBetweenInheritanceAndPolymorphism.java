package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between inheritance and polymorphism?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson30_WhatIsTheDifferenceBetweenInheritanceAndPolymorphism {
    public static void main(String[] args) {
        System.out.println("=== INHERITANCE VS POLYMORPHISM ===\n");
        System.out.println("Inheritance:");
        System.out.println("  - Mechanism where class acquires properties of another");
        System.out.println("  - Uses extends keyword");
        System.out.println("  - Establishes parent-child relationship");
        System.out.println("  - Code reuse");
        System.out.println();
        System.out.println("Polymorphism:");
        System.out.println("  - Ability of object to take multiple forms");
        System.out.println("  - Achieved via method overriding/overloading");
        System.out.println("  - Parent reference can hold child object");
        System.out.println("  - Runtime behavior flexibility");
        System.out.println();
        System.out.println("Inheritance enables polymorphism");
    }
}
