package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between for loop and for-each loop?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of iteration constructs
 * - Shows knowledge of enhanced for loop
 * - Reveals awareness of when to use each
 */

public class Lesson17_WhatIsTheDifferenceBetweenForLoopAndForEachLoop {

    public static void main(String[] args) {
        System.out.println("=== FOR LOOP VS FOR-EACH LOOP ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between for loop and for-each loop?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("FOR LOOP (traditional):");
        System.out.println("  - Requires index/counter variable");
        System.out.println("  - Can iterate in any direction");
        System.out.println("  - Can modify elements during iteration");
        System.out.println("  - Can access index during iteration");
        System.out.println("  - Syntax: for (int i = 0; i < n; i++)");
        System.out.println();

        System.out.println("FOR-EACH LOOP (enhanced for):");
        System.out.println("  - No index variable needed");
        System.out.println("  - Iterates in forward direction only");
        System.out.println("  - Cannot modify collection during iteration");
        System.out.println("  - Cannot access index");
        System.out.println("  - Syntax: for (Type item : collection)");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Index access: for yes, for-each no");
        System.out.println("  2. Direction: for any, for-each forward only");
        System.out.println("  3. Modification: for can modify, for-each read-only");
        System.out.println("  4. Syntax: for more verbose, for-each cleaner");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        int[] numbers = {1, 2, 3, 4, 5};

        System.out.println("Traditional for loop:");
        System.out.println("  for (int i = 0; i < numbers.length; i++) {");
        System.out.println("    System.out.println(numbers[i]);");
        System.out.println("    // Can modify: numbers[i] = numbers[i] * 2;");
        System.out.println("  }");
        System.out.println();

        System.out.println("For-each loop:");
        System.out.println("  for (int num : numbers) {");
        System.out.println("    System.out.println(num);");
        System.out.println("    // Cannot modify original array");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using for-each when you need index:");
        System.out.println("   - Use traditional for if you need i");
        System.out.println();

        System.out.println("2. Trying to modify collection in for-each:");
        System.out.println("   - Causes ConcurrentModificationException");
        System.out.println("   - Use iterator.remove() or traditional for");
        System.out.println();

        System.out.println("3. Using for-each with null:");
        System.out.println("   - Throws NullPointerException");
        System.out.println("   - Check for null first");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. Can you modify elements in a for-each loop?");
        System.out.println("2. What happens if you modify a List during for-each?");
        System.out.println("3. When would you use traditional for over for-each?");
        System.out.println("4. Can you use for-each with arrays?");
        System.out.println("5. What is the difference between for-each and iterator?");
    }
}
