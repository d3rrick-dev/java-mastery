package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between abstract class and interface?
 *
 * Difficulty: ADVANCED
 */

public class Lesson04_WhatIsTheDifferenceBetweenAbstractClassAndInterface {
    public static void main(String[] args) {
        System.out.println("=== ABSTRACT CLASS VS INTERFACE ===\n");
        System.out.println("Abstract Class:");
        System.out.println("  - Can have both abstract and concrete methods");
        System.out.println("  - Can have instance variables (state)");
        System.out.println("  - Supports constructors");
        System.out.println("  - Single inheritance (extends)");
        System.out.println("  - Can have protected, private members");
        System.out.println();
        System.out.println("Interface (Java 8+):");
        System.out.println("  - All methods are public abstract by default");
        System.out.println("  - Can have default and static methods (Java 8+)");
        System.out.println("  - Can have private methods (Java 9+)");
        System.out.println("  - No instance variables (only constants)");
        System.out.println("  - Multiple inheritance (implements)");
        System.out.println();
        System.out.println("When to use:");
        System.out.println("  - Abstract class: shared code, common state, template method pattern");
        System.out.println("  - Interface: contract, multiple inheritance, plugin architecture");
    }
}
