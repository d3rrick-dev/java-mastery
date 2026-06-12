package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between this() and super()?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of inheritance
 * - Shows knowledge of constructor chaining
 * - Reveals awareness of object initialization
 */

public class Lesson18_WhatIsTheDifferenceBetweenThisAndSuper {

    public static void main(String[] args) {
        System.out.println("=== THIS() VS SUPER() ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between this() and super()?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("THIS:");
        System.out.println("  - Refers to current class instance");
        System.out.println("  - Used to call current class constructor (this())");
        System.out.println("  - Used to access current class members");
        System.out.println("  - Must be first statement in constructor");
        System.out.println();

        System.out.println("SUPER:");
        System.out.println("  - Refers to parent class instance");
        System.out.println("  - Used to call parent class constructor (super())");
        System.out.println("  - Used to access parent class members");
        System.out.println("  - Must be first statement in constructor");
        System.out.println();

        System.out.println("KEY RULES:");
        System.out.println("  1. Both must be FIRST statement in constructor");
        System.out.println("  2. Cannot use both this() and super() in same constructor");
        System.out.println("  3. If not specified, compiler adds super() automatically");
        System.out.println("  4. this() calls overloaded constructor in same class");
        System.out.println("  5. super() calls parent class constructor");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("class Animal {");
        System.out.println("  Animal() { System.out.println(\"Animal\"); }");
        System.out.println("}");
        System.out.println();
        System.out.println("class Dog extends Animal {");
        System.out.println("  Dog() {");
        System.out.println("    super();  // Calls Animal() - MUST be first");
        System.out.println("    System.out.println(\"Dog\");");
        System.out.println("  }");
        System.out.println("}");
        System.out.println();

        System.out.println("Constructor chaining with this():");
        System.out.println("  class Person {");
        System.out.println("    Person() { this(\"Unknown\"); }  // Calls other constructor");
        System.out.println("    Person(String name) { this.name = name; }");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using both this() and super():");
        System.out.println("   - Only one can be first statement");
        System.out.println("   - Compile error if both used");
        System.out.println();

        System.out.println("2. Forgetting super() call:");
        System.out.println("   - Compiler adds it automatically");
        System.out.println("   - Calls parent's no-arg constructor");
        System.out.println("   - Error if parent has no no-arg constructor");
        System.out.println();

        System.out.println("3. Calling instance methods in constructor:");
        System.out.println("   - this.method() in constructor can be dangerous");
        System.out.println("   - Object not fully initialized yet");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What happens if parent has no default constructor?");
        System.out.println("2. Can you call super() from a method?");
        System.out.println("3. What is constructor chaining?");
        System.out.println("4. Can you use this() in a static method?");
        System.out.println("5. What is the order of constructor execution?");
    }
}
