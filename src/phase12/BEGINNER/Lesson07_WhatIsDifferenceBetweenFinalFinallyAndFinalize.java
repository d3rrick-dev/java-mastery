package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between final, finally, and finalize?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of Java keywords
 * - Shows knowledge of exception handling
 * - Reveals awareness of deprecated features
 */

public class Lesson07_WhatIsDifferenceBetweenFinalFinallyAndFinalize {

    public static void main(String[] args) {
        System.out.println("=== FINAL VS FINALLY VS FINALIZE ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between final, finally, and finalize?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("1. FINAL:");
        System.out.println("   - Keyword for restrictions");
        System.out.println("   - final class: Cannot be extended");
        System.out.println("   - final method: Cannot be overridden");
        System.out.println("   - final variable: Cannot be reassigned (constant)");
        System.out.println();

        System.out.println("2. FINALLY:");
        System.out.println("   - Block for cleanup code");
        System.out.println("   - Always executes (except System.exit())");
        System.out.println("   - Used with try-catch");
        System.out.println("   - For resource cleanup");
        System.out.println();

        System.out.println("3. FINALIZE:");
        System.out.println("   - Method called by GC before object removal");
        System.out.println("   - Deprecated in Java 9");
        System.out.println("   - Not guaranteed to be called");
        System.out.println("   - Use try-with-resources instead");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("final class: Cannot extend");
        System.out.println("  final class Animal { }");
        System.out.println("  class Dog extends Animal { }  // Compile error!");
        System.out.println();

        System.out.println("finally block: Always executes");
        System.out.println("  try {");
        System.out.println("    // risky code");
        System.out.println("  } catch (Exception e) {");
        System.out.println("    // handle");
        System.out.println("  } finally {");
        System.out.println("    // cleanup - ALWAYS runs");
        System.out.println("  }");
        System.out.println();

        System.out.println("finalize() method: Deprecated!");
        System.out.println("  protected void finalize() throws Throwable { }");
        System.out.println("  // DON'T USE - deprecated in Java 9");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Confusing the three:");
        System.out.println("   - They are completely different");
        System.out.println("   - final = restriction, finally = cleanup, finalize = GC");
        System.out.println();

        System.out.println("2. Using finalize():");
        System.out.println("   - Deprecated and unreliable");
        System.out.println("   - Use try-with-resources or Cleaner");
        System.out.println();

        System.out.println("3. Thinking finally always executes:");
        System.out.println("   - System.exit() prevents it");
        System.out.println("   - JVM crash prevents it");
        System.out.println("   - Infinite loop in try prevents it");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is try-with-resources?");
        System.out.println("2. When does finally NOT execute?");
        System.out.println("3. What replaced finalize() in Java 9?");
        System.out.println("4. Can a method be both final and abstract?");
        System.out.println("5. What is a blank final variable?");
    }
}
