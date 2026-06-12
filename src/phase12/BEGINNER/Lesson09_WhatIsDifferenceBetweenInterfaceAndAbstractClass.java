package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between interface and abstract class?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests OOP design understanding
 * - Shows knowledge of Java language features
 * - Reveals when to use what
 */

public class Lesson09_WhatIsDifferenceBetweenInterfaceAndAbstractClass {

    public static void main(String[] args) {
        System.out.println("=== INTERFACE VS ABSTRACT CLASS ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between interface and abstract class?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("ABSTRACT CLASS:");
        System.out.println("  - Can have abstract and concrete methods");
        System.out.println("  - Can have instance variables (state)");
        System.out.println("  - Can have constructors");
        System.out.println("  - Single inheritance (extends)");
        System.out.println("  - Can have any access modifiers");
        System.out.println("  - Use when: 'Is-a' relationship, shared code");
        System.out.println();

        System.out.println("INTERFACE (Java 8+):");
        System.out.println("  - All methods public abstract by default");
        System.out.println("  - Can have default and static methods (Java 8)");
        System.out.println("  - Can have private methods (Java 9)");
        System.out.println("  - Only public static final constants");
        System.out.println("  - Multiple inheritance (implements)");
        System.out.println("  - Use when: 'Can-do' relationship, contract");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Multiple inheritance: Interface yes, Abstract class no");
        System.out.println("  2. State: Abstract class yes, Interface no (only constants)");
        System.out.println("  3. Constructors: Abstract class yes, Interface no");
        System.out.println("  4. Access modifiers: Abstract class any, Interface public only");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Abstract class example:");
        System.out.println("  abstract class Vehicle {");
        System.out.println("    protected String brand;  // state");
        System.out.println("    public Vehicle(String brand) {  // constructor");
        System.out.println("      this.brand = brand;");
        System.out.println("    }");
        System.out.println("    public abstract void start();");
        System.out.println("    public void stop() {  // concrete method");
        System.out.println("      System.out.println(\"Stopping\");");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();

        System.out.println("Interface example (Java 8+):");
        System.out.println("  interface Drivable {");
        System.out.println("    void drive();  // abstract");
        System.out.println("    default void honk() {  // default method");
        System.out.println("      System.out.println(\"Beep!\");");
        System.out.println("    }");
        System.out.println("    static int getWheels() {  // static method");
        System.out.println("      return 4;");
        System.out.println("    }");
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
        System.out.println("   - Java 8 changed interfaces significantly");
        System.out.println("   - They're no longer 'pure abstract'");
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
