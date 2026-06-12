package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between composition and inheritance?
 *
 * Difficulty: ADVANCED
 */

public class Lesson50_WhatIsTheDifferenceBetweenCompositionAndInheritance {
    public static void main(String[] args) {
        System.out.println("=== COMPOSITION VS INHERITANCE ===\n");
        System.out.println("Inheritance (IS-A relationship):");
        System.out.println("  - Child class extends parent class");
        System.out.println("  - Represents IS-A relationship");
        System.out.println("  - Tightly coupled to parent");
        System.out.println("  - Changes in parent affect child");
        System.out.println("  - Single inheritance only");
        System.out.println("  - Example: class Dog extends Animal");
        System.out.println();
        System.out.println("Composition (HAS-A relationship):");
        System.out.println("  - Class contains instance of another class");
        System.out.println("  - Represents HAS-A relationship");
        System.out.println("  - Loosely coupled");
        System.out.println("  - Changes in contained class don't affect container");
        System.out.println("  - Multiple compositions possible");
        System.out.println("  - Example: class Car { Engine engine; }");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Relationship: IS-A vs HAS-A");
        System.out.println("  - Coupling: tight vs loose");
        System.out.println("  - Flexibility: less vs more");
        System.out.println("  - Reuse: white-box vs black-box");
        System.out.println("  - Testing: harder vs easier");
        System.out.println();
        System.out.println("Design Principle:");
        System.out.println("  - Favor composition over inheritance");
        System.out.println("  - Inheritance breaks encapsulation");
        System.out.println("  - Composition provides better flexibility");
        System.out.println("  - Use inheritance for true IS-A relationships");
    }
}
