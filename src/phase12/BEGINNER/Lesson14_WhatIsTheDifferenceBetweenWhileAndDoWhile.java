package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between while and do-while loop?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of loop constructs
 * - Shows knowledge of execution flow
 * - Reveals attention to detail
 */

public class Lesson14_WhatIsTheDifferenceBetweenWhileAndDoWhile {

    public static void main(String[] args) {
        System.out.println("=== WHILE VS DO-WHILE ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between while and do-while loop?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("WHILE LOOP:");
        System.out.println("  - Entry-controlled loop");
        System.out.println("  - Condition checked BEFORE execution");
        System.out.println("  - May not execute at all (if condition false initially)");
        System.out.println("  - Syntax: while (condition) { }");
        System.out.println();

        System.out.println("DO-WHILE LOOP:");
        System.out.println("  - Exit-controlled loop");
        System.out.println("  - Condition checked AFTER execution");
        System.out.println("  - Always executes at least once");
        System.out.println("  - Syntax: do { } while (condition);  // Note semicolon!");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("while loop (condition checked first):");
        System.out.println("  int i = 5;");
        System.out.println("  while (i < 5) {  // false initially");
        System.out.println("    System.out.println(i);  // NEVER executes");
        System.out.println("  }");
        System.out.println();

        System.out.println("do-while loop (body executes first):");
        System.out.println("  int i = 5;");
        System.out.println("  do {");
        System.out.println("    System.out.println(i);  // Executes once!");
        System.out.println("  } while (i < 5);  // then check condition");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Forgetting semicolon in do-while:");
        System.out.println("   - do { } while (condition)  // Missing semicolon - compile error!");
        System.out.println("   - do { } while (condition);  // Correct");
        System.out.println();

        System.out.println("2. Using do-while when while is better:");
        System.out.println("   - do-while always executes once");
        System.out.println("   - Use only when at least one iteration needed");
        System.out.println();

        System.out.println("3. Infinite loops:");
        System.out.println("   - while (true) { }  // Infinite");
        System.out.println("   - do { } while (true);  // Also infinite");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is an infinite loop?");
        System.out.println("2. How do you create an infinite loop?");
        System.out.println("3. What is the difference between for and while?");
        System.out.println("4. When would you use do-while?");
        System.out.println("5. Can you have nested loops?");
    }
}
