package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between abstract class and interface?
 *
 * Difficulty: BEGINNER
 */

public class Lesson31_WhatIsTheDifferenceBetweenAbstractClassAndInterface {
    public static void main(String[] args) {
        System.out.println("=== ABSTRACT CLASS VS INTERFACE ===\n");
        System.out.println("Abstract Class:");
        System.out.println("  - Can have abstract and concrete methods");
        System.out.println("  - Can have instance variables");
        System.out.println("  - Supports single inheritance");
        System.out.println("  - Can have constructors");
        System.out.println();
        System.out.println("Interface:");
        System.out.println("  - All methods abstract (before Java 8)");
        System.out.println("  - Can have default/static methods (Java 8+)");
        System.out.println("  - Cannot have instance variables (only constants)");
        System.out.println("  - Supports multiple inheritance");
        System.out.println("  - No constructors");
        System.out.println();
        System.out.println("Use abstract class when: Shared code, state, or template pattern");
        System.out.println("Use interface when: Multiple inheritance, contract definition");
    }
}
