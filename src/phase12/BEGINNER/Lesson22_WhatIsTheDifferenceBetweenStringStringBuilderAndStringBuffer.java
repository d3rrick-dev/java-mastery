package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between String, StringBuilder, and StringBuffer?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of string handling
 * - Shows knowledge of mutability
 * - Reveals awareness of thread safety
 */

public class Lesson22_WhatIsTheDifferenceBetweenStringStringBuilderAndStringBuffer {

    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUILDER VS STRINGBUFFER ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between String, StringBuilder, and StringBuffer?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("STRING:");
        System.out.println("  - Immutable (cannot be changed)");
        System.out.println("  - Thread-safe (immutable = safe)");
        System.out.println("  - Every modification creates new object");
        System.out.println("  - Use for fixed text, constants");
        System.out.println();

        System.out.println("STRINGBUILDER:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - NOT thread-safe");
        System.out.println("  - Faster (no synchronization)");
        System.out.println("  - Use for single-threaded string building");
        System.out.println();

        System.out.println("STRINGBUFFER:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Slower (synchronization overhead)");
        System.out.println("  - Use for multi-threaded string building");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Mutability: String immutable, others mutable");
        System.out.println("  2. Thread safety: String and StringBuffer safe, StringBuilder not");
        System.out.println("  3. Performance: StringBuilder fastest, String slowest for modifications");
        System.out.println("  4. Since: String (Java 1), StringBuilder (Java 5), StringBuffer (Java 1)");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        String s = "hello";
        s = s.concat(" world");  // Creates NEW string
        System.out.println("String: " + s);

        StringBuilder sb = new StringBuilder("hello");
        sb.append(" world");  // Modifies SAME object
        System.out.println("StringBuilder: " + sb);

        StringBuffer sbf = new StringBuffer("hello");
        sbf.append(" world");  // Thread-safe modification
        System.out.println("StringBuffer: " + sbf);
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using + in loops:");
        System.out.println("   - Creates many intermediate String objects");
        System.out.println("   - Use StringBuilder instead");
        System.out.println();

        System.out.println("2. Using StringBuffer in single-threaded code:");
        System.out.println("   - Unnecessary synchronization overhead");
        System.out.println("   - Use StringBuilder instead");
        System.out.println();

        System.out.println("3. Thinking StringBuffer is always needed for thread safety:");
        System.out.println("   - Often local variables are thread-safe by default");
        System.out.println("   - Only needed when shared between threads");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. Why is String immutable?");
        System.out.println("2. What is the String pool?");
        System.out.println("3. When would you use StringBuffer over StringBuilder?");
        System.out.println("4. How does String concatenation work internally?");
        System.out.println("5. What is the initial capacity of StringBuilder?");
    }
}
