package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between interface and abstract class?
 *
 * Difficulty: BEGINNER
 */

public class Lesson48_WhatIsTheDifferenceBetweenInterfaceAndAbstractClass {
    public static void main(String[] args) {
        System.out.println("=== INTERFACE VS ABSTRACT CLASS ===\n");
        System.out.println("Interface:");
        System.out.println("  - All methods abstract (before Java 8)");
        System.out.println("  - Can have default/static methods (Java 8+)");
        System.out.println("  - Multiple inheritance supported");
        System.out.println("  - No constructors");
        System.out.println("  - Only constants (public static final)");
        System.out.println();
        System.out.println("Abstract Class:");
        System.out.println("  - Can have abstract and concrete methods");
        System.out.println("  - Single inheritance only");
        System.out.println("  - Can have constructors");
        System.out.println("  - Can have instance variables");
        System.out.println();
        System.out.println("Use interface for: Contract definition, multiple inheritance");
        System.out.println("Use abstract class for: Shared code, state, template pattern");
    }
}
