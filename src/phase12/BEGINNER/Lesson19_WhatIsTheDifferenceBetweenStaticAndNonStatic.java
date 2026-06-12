package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between static and non-static?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of class vs instance members
 * - Shows knowledge of memory allocation
 * - Reveals understanding of when to use static
 */

public class Lesson19_WhatIsTheDifferenceBetweenStaticAndNonStatic {

    public static void main(String[] args) {
        System.out.println("=== STATIC VS NON-STATIC ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between static and non-static?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("STATIC:");
        System.out.println("  - Belongs to CLASS, not instances");
        System.out.println("  - Shared among all instances");
        System.out.println("  - Loaded when class is loaded");
        System.out.println("  - Can be accessed without creating object");
        System.out.println("  - Stored in method area (Metaspace)");
        System.out.println();

        System.out.println("NON-STATIC (instance):");
        System.out.println("  - Belongs to INSTANCE, not class");
        System.out.println("  - Each object has its own copy");
        System.out.println("  - Loaded when object is created");
        System.out.println("  - Requires object to access");
        System.out.println("  - Stored in heap");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Memory: static in method area, non-static in heap");
        System.out.println("  2. Access: static via class, non-static via object");
        System.out.println("  3. Sharing: static shared, non-static per instance");
        System.out.println("  4. this/super: Cannot use in static context");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("class Counter {");
        System.out.println("  static int count = 0;      // Shared");
        System.out.println("  int instanceCount = 0;     // Per object");
        System.out.println("}");
        System.out.println();
        System.out.println("Counter c1 = new Counter();");
        System.out.println("Counter c2 = new Counter();");
        System.out.println("c1.count++;    // Affects all instances");
        System.out.println("c2.count++;    // Same counter!");
        System.out.println("c1.instanceCount++;  // Only c1 affected");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using this/super in static methods:");
        System.out.println("   - Static methods don't have 'this'");
        System.out.println("   - Compile error");
        System.out.println();

        System.out.println("2. Making everything static:");
        System.out.println("   - Breaks OOP principles");
        System.out.println("   - Use only when truly shared");
        System.out.println();

        System.out.println("3. Static methods for instance behavior:");
        System.out.println("   - Static cannot access non-static directly");
        System.out.println("   - Need object reference");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. Can static methods be overridden?");
        System.out.println("2. What is a static block?");
        System.out.println("3. Can you access non-static from static method?");
        System.out.println("4. What is the order of static initialization?");
        System.out.println("5. When should you use static?");
    }
}
