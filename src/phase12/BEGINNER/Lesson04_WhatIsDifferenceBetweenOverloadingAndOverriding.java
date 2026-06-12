package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between method overloading and overriding?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests OOP fundamentals
 * - Shows understanding of polymorphism
 * - Reveals knowledge of compile-time vs runtime binding
 */

public class Lesson04_WhatIsDifferenceBetweenOverloadingAndOverriding {

    public static void main(String[] args) {
        System.out.println("=== OVERLOADING VS OVERRIDING ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between method overloading and overriding?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("METHOD OVERLOADING:");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Happens in SAME class");
        System.out.println("  - Compile-time polymorphism (static binding)");
        System.out.println("  - Return type can be different");
        System.out.println("  - Access modifiers can be different");
        System.out.println();

        System.out.println("METHOD OVERRIDING:");
        System.out.println("  - Same method name, same parameters");
        System.out.println("  - Happens in PARENT and CHILD classes");
        System.out.println("  - Runtime polymorphism (dynamic binding)");
        System.out.println("  - Return type must be same or covariant");
        System.out.println("  - Cannot have more restrictive access modifier");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Overloading (same class):");
        System.out.println("  class Calculator {");
        System.out.println("    int add(int a, int b) { return a + b; }");
        System.out.println("    double add(double a, double b) { return a + b; }");
        System.out.println("    int add(int a, int b, int c) { return a + b + c; }");
        System.out.println("  }");
        System.out.println();

        System.out.println("Overriding (parent-child):");
        System.out.println("  class Animal {");
        System.out.println("    void sound() { System.out.println(\"Animal sound\"); }");
        System.out.println("  }");
        System.out.println("  class Dog extends Animal {");
        System.out.println("    @Override");
        System.out.println("    void sound() { System.out.println(\"Bark\"); }");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // KEY DIFFERENCES TABLE
        // ============================================================
        System.out.println("--- Key Differences ---\n");

        System.out.println("| Aspect           | Overloading      | Overriding        |");
        System.out.println("|------------------|------------------|-------------------|");
        System.out.println("| Scope            | Same class       | Parent-child      |");
        System.out.println("| Parameters       | Must differ      | Must be same      |");
        System.out.println("| Binding          | Compile-time     | Runtime           |");
        System.out.println("| Performance      | Faster           | Slightly slower   |");
        System.out.println("| @Override        | Not used         | Required          |");
        System.out.println("| Return type      | Can differ       | Must be same      |");
        System.out.println("| Access modifier  | Can differ       | Cannot reduce     |");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Changing only return type:");
        System.out.println("   - NOT valid overloading (compile error)");
        System.out.println("   - Parameters must differ");
        System.out.println();

        System.out.println("2. Forgetting @Override:");
        System.out.println("   - Typos create new method instead of override");
        System.out.println("   - @Override catches this at compile time");
        System.out.println();

        System.out.println("3. Making overridden method more restrictive:");
        System.out.println("   - Cannot reduce visibility");
        System.out.println("   - public -> protected is NOT allowed");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is covariant return type?");
        System.out.println("2. Can you override static methods?");
        System.out.println("3. Can you override private methods?");
        System.out.println("4. What is method hiding?");
        System.out.println("5. What is the difference between polymorphism and overriding?");
    }
}
