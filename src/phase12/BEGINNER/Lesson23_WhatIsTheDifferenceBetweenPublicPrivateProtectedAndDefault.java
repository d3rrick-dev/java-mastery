package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between public, private, protected, and default?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of access modifiers
 * - Shows knowledge of encapsulation
 * - Reveals understanding of package structure
 */

public class Lesson23_WhatIsTheDifferenceBetweenPublicPrivateProtectedAndDefault {

    public static void main(String[] args) {
        System.out.println("=== ACCESS MODIFIERS ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What are the access modifiers in Java?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("1. PUBLIC:");
        System.out.println("   - Accessible from anywhere");
        System.out.println("   - No restrictions");
        System.out.println();

        System.out.println("2. PRIVATE:");
        System.out.println("   - Accessible only within same class");
        System.out.println("   - Most restrictive");
        System.out.println();

        System.out.println("3. PROTECTED:");
        System.out.println("   - Accessible within package");
        System.out.println("   - Accessible in subclasses (even different package)");
        System.out.println();

        System.out.println("4. DEFAULT (package-private):");
        System.out.println("   - Accessible only within same package");
        System.out.println("   - No keyword needed");
        System.out.println();

        System.out.println("ACCESS MATRIX:");
        System.out.println("| Modifier | Class | Package | Subclass | World |");
        System.out.println("|----------|-------|---------|----------|-------|");
        System.out.println("| public   |   Y   |    Y    |    Y     |   Y   |");
        System.out.println("| protected|   Y   |    Y    |    Y     |   N   |");
        System.out.println("| default  |   Y   |    Y    |    N     |   N   |");
        System.out.println("| private  |   Y   |    N    |    N     |   N   |");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using public for everything:");
        System.out.println("   - Breaks encapsulation");
        System.out.println("   - Use most restrictive access possible");
        System.out.println();

        System.out.println("2. Confusing protected and default:");
        System.out.println("   - protected: visible to subclasses");
        System.out.println("   - default: NOT visible to subclasses");
        System.out.println();

        System.out.println("3. Not knowing private methods in interfaces (Java 9+):");
        System.out.println("   - Private methods in interfaces are allowed");
        System.out.println("   - Used for helper methods in default methods");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is the default access modifier?");
        System.out.println("2. Can a class be protected?");
        System.out.println("3. What access modifier for constants?");
        System.out.println("4. Can you reduce visibility when overriding?");
        System.out.println("5. What is the most restrictive access modifier?");
    }
}
