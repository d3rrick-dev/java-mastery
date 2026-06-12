package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between break and continue?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of loop control
 * - Shows knowledge of flow control statements
 * - Reveals ability to write clean loops
 */

public class Lesson13_WhatIsTheDifferenceBetweenBreakAndContinue {

    public static void main(String[] args) {
        System.out.println("=== BREAK VS CONTINUE ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between break and continue?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("BREAK:");
        System.out.println("  - Terminates the loop/switch immediately");
        System.out.println("  - Control goes to statement after loop");
        System.out.println("  - Used to exit loop early");
        System.out.println();

        System.out.println("CONTINUE:");
        System.out.println("  - Skips current iteration only");
        System.out.println("  - Control goes to next iteration");
        System.out.println("  - Used to skip specific values");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("break example:");
        System.out.println("  for (int i = 0; i < 10; i++) {");
        System.out.println("    if (i == 5) break;  // Exit loop");
        System.out.println("    System.out.println(i);  // Prints 0-4");
        System.out.println("  }");
        System.out.println();

        System.out.println("continue example:");
        System.out.println("  for (int i = 0; i < 10; i++) {");
        System.out.println("    if (i == 5) continue;  // Skip this iteration");
        System.out.println("    System.out.println(i);  // Prints 0-4, 6-9");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // LABELED BREAK/CONTINUE
        // ============================================================
        System.out.println("--- Labeled Break/Continue ---\n");

        System.out.println("outer: for (int i = 0; i < 3; i++) {");
        System.out.println("  for (int j = 0; j < 3; j++) {");
        System.out.println("    if (i == 1 && j == 1) break outer;  // Break outer loop");
        System.out.println("  }");
        System.out.println("}");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using break when continue is needed:");
        System.out.println("   - break exits entire loop");
        System.out.println("   - continue skips only current iteration");
        System.out.println();

        System.out.println("2. Forgetting labeled break/continue:");
        System.out.println("   - break/continue only affects innermost loop");
        System.out.println("   - Use labels for nested loops");
        System.out.println();

        System.out.println("3. Overusing break/continue:");
        System.out.println("   - Can make code hard to read");
        System.out.println("   - Consider restructuring loop condition");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is a labeled break?");
        System.out.println("2. Can you use break in switch statements?");
        System.out.println("3. What happens if you forget break in switch?");
        System.out.println("4. Is there a way to exit multiple nested loops?");
        System.out.println("5. What is the difference between return and break?");
    }
}
