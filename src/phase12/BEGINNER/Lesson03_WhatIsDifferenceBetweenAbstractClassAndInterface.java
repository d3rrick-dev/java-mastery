package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between abstract class and interface?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests OOP fundamentals
 * - Shows understanding of inheritance vs composition
 * - Reveals knowledge of Java evolution (default methods)
 */

public class Lesson03_WhatIsDifferenceBetweenAbstractClassAndInterface {

    public static void main(String[] args) {
        System.out.println("=== ABSTRACT CLASS VS INTERFACE ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between abstract class and interface?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("1. MULTIPLE INHERITANCE:");
        System.out.println("   - Abstract class: Single inheritance (extends)");
        System.out.println("   - Interface: Multiple inheritance (implements)");
        System.out.println();

        System.out.println("2. METHODS:");
        System.out.println("   - Abstract class: Can have abstract + concrete methods");
        System.out.println("   - Interface (Java 8+): Can have default, static, private methods");
        System.out.println();

        System.out.println("3. VARIABLES:");
        System.out.println("   - Abstract class: Can have any type of variables");
        System.out.println("   - Interface: Only public static final constants");
        System.out.println();

        System.out.println("4. CONSTRUCTORS:");
        System.out.println("   - Abstract class: Can have constructors");
        System.out.println("   - Interface: Cannot have constructors");
        System.out.println();

        System.out.println("5. ACCESS MODIFIERS:");
        System.out.println("   - Abstract class: Can have public, protected, private members");
        System.out.println("   - Interface: Methods are public by default");
        System.out.println();

        System.out.println("6. STATE:");
        System.out.println("   - Abstract class: Can have instance variables (state)");
        System.out.println("   - Interface: Cannot have instance variables");
        System.out.println();

        System.out.println("7. WHEN TO USE:");
        System.out.println("   - Abstract class: 'Is-a' relationship, shared code");
        System.out.println("   - Interface: 'Can-do' relationship, contract");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Abstract class example:");
        System.out.println("  abstract class Animal {");
        System.out.println("    protected String name;");
        System.out.println("    public abstract void sound();");
        System.out.println("    public void sleep() { System.out.println(\"Sleeping\"); }");
        System.out.println("  }");
        System.out.println();

        System.out.println("Interface example (Java 8+):");
        System.out.println("  interface Vehicle {");
        System.out.println("    void start();  // abstract");
        System.out.println("    default void honk() { System.out.println(\"Beep!\"); }");
        System.out.println("    static int getWheels() { return 4; }");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using interface when abstract class is better:");
        System.out.println("   - If you need to add common code, use abstract class");
        System.out.println();

        System.out.println("2. Not knowing about default methods:");
        System.out.println("   - Java 8 added default methods to interfaces");
        System.out.println("   - Interfaces are no longer 'pure abstract'");
        System.out.println();

        System.out.println("3. Thinking interfaces can have state:");
        System.out.println("   - Only public static final constants");
        System.out.println("   - No instance variables");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. When would you choose abstract class over interface?");
        System.out.println("2. What are default methods in interfaces?");
        System.out.println("3. Can an interface extend multiple interfaces?");
        System.out.println("4. What is a marker interface?");
        System.out.println("5. What is the difference between extends and implements?");
    }
}
