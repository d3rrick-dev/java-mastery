package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: What is a constructor in Java?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests OOP fundamentals
 * - Shows understanding of object creation
 * - Reveals knowledge of initialization
 */

public class Lesson12_WhatIsAConstructor {

    public static void main(String[] args) {
        System.out.println("=== CONSTRUCTORS IN JAVA ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is a constructor in Java?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("CONSTRUCTOR:");
        System.out.println("  - Special method to initialize objects");
        System.out.println("  - Same name as class");
        System.out.println("  - No return type (not even void)");
        System.out.println("  - Called automatically when object created");
        System.out.println("  - Used for initialization, not business logic");
        System.out.println();

        System.out.println("TYPES OF CONSTRUCTORS:");
        System.out.println("  1. Default constructor (no-arg)");
        System.out.println("  2. Parameterized constructor");
        System.out.println("  3. Copy constructor");
        System.out.println();

        System.out.println("RULES:");
        System.out.println("  - Must have same name as class");
        System.out.println("  - Cannot have return type");
        System.out.println("  - Cannot be abstract, static, final, synchronized");
        System.out.println("  - Can be private (for singleton)");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("class Person {");
        System.out.println("  private String name;");
        System.out.println("  private int age;");
        System.out.println();
        System.out.println("  // Default constructor");
        System.out.println("  public Person() {");
        System.out.println("    this.name = \"Unknown\";");
        System.out.println("    this.age = 0;");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // Parameterized constructor");
        System.out.println("  public Person(String name, int age) {");
        System.out.println("    this.name = name;");
        System.out.println("    this.age = age;");
        System.out.println("  }");
        System.out.println("}");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Adding return type:");
        System.out.println("   - public void Person() {}  // This is a method, not constructor!");
        System.out.println();

        System.out.println("2. Forgetting this() for overloading:");
        System.out.println("   - Use this() to call another constructor");
        System.out.println("   - Must be first statement");
        System.out.println();

        System.out.println("3. Doing heavy work in constructor:");
        System.out.println("   - Constructors should be fast");
        System.out.println("   - Use factory methods for complex creation");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is constructor chaining?");
        System.out.println("2. What is a copy constructor?");
        System.out.println("3. Can you call a constructor from another?");
        System.out.println("4. Can a constructor be private?");
        System.out.println("5. What happens if you don't define any constructor?");
    }
}
