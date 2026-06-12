package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between char and varchar?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of data types
 * - Shows knowledge of string handling
 * - Reveals awareness of memory usage
 */

public class Lesson20_WhatIsTheDifferenceBetweenCharAndVarchar {

    public static void main(String[] args) {
        System.out.println("=== CHAR vs VARCHAR ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between char and varchar?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("CHAR:");
        System.out.println("  - Fixed-length character data type");
        System.out.println("  - Always uses specified length");
        System.out.println("  - Padded with spaces if shorter");
        System.out.println("  - Faster for fixed-length data");
        System.out.println("  - Example: CHAR(10) always uses 10 characters");
        System.out.println();

        System.out.println("VARCHAR:");
        System.out.println("  - Variable-length character data type");
        System.out.println("  - Uses only needed length + 1 or 2 bytes overhead");
        System.out.println("  - No padding");
        System.out.println("  - Better for variable-length data");
        System.out.println("  - Example: VARCHAR(255) uses actual length + overhead");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Storage: CHAR fixed, VARCHAR variable");
        System.out.println("  2. Performance: CHAR faster for fixed data");
        System.out.println("  3. Space: VARCHAR more efficient for variable data");
        System.out.println("  4. Padding: CHAR pads, VARCHAR doesn't");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("CHAR(10) storing 'Hi':");
        System.out.println("  Actual: 'Hi        ' (10 chars, padded with spaces)");
        System.out.println("  Storage: 10 bytes");
        System.out.println();
        System.out.println("VARCHAR(255) storing 'Hi':");
        System.out.println("  Actual: 'Hi' (2 chars)");
        System.out.println("  Storage: 2 bytes + 1 byte overhead = 3 bytes");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using CHAR for everything:");
        System.out.println("   - Wastes space for variable-length data");
        System.out.println("   - Use VARCHAR for names, emails, etc.");
        System.out.println();

        System.out.println("2. Using VARCHAR for fixed-length data:");
        System.out.println("   - Slight overhead for length storage");
        System.out.println("   - Use CHAR for codes, flags, etc.");
        System.out.println();

        System.out.println("3. Not considering maximum length:");
        System.out.println("   - VARCHAR(n) has max n characters");
        System.out.println("   - VARCHAR(MAX) in some databases");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. When would you use CHAR over VARCHAR?");
        System.out.println("2. What is the maximum length of VARCHAR?");
        System.out.println("3. What is TEXT data type?");
        System.out.println("4. How does storage differ between databases?");
        System.out.println("5. What is the performance difference?");
    }
}
