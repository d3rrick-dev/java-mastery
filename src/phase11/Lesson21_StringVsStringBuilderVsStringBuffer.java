package phase11;

/**
 * LESSON 21: STRING VS STRINGBUILDER VS STRINGBUFFER
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * String: Immutable, thread-safe
 * StringBuilder: Mutable, NOT thread-safe, faster
 * StringBuffer: Mutable, thread-safe (synchronized), slower
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - String: For immutable text
 * - StringBuilder: For single-threaded mutable text
 * - StringBuffer: For multi-threaded mutable text
 */

public class Lesson21_StringVsStringBuilderVsStringBuffer {

    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUILDER VS STRINGBUFFER ===\n");

        // ============================================================
        // EXAMPLE 1: String immutability
        // ============================================================
        System.out.println("--- Example 1: String Immutability ---\n");

        String s = "hello";
        s = s.concat(" world");  // Creates NEW string

        System.out.println("Original: hello");
        System.out.println("After concat: " + s);
        System.out.println("String is immutable - creates new object");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: StringBuilder
        // ============================================================
        System.out.println("--- Example 2: StringBuilder ---\n");

        StringBuilder sb = new StringBuilder("hello");
        sb.append(" world");  // Modifies SAME object

        System.out.println("StringBuilder: " + sb);
        System.out.println("Mutable - modifies same object");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: StringBuffer
        // ============================================================
        System.out.println("--- Example 3: StringBuffer ---\n");

        StringBuffer sbf = new StringBuffer("hello");
        sbf.append(" world");  // Thread-safe modification

        System.out.println("StringBuffer: " + sbf);
        System.out.println("Thread-safe but slower (synchronized)");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Performance comparison
        // ============================================================
        System.out.println("--- Example 4: Performance ---\n");

        int iterations = 10000;

        // String concatenation
        long start = System.nanoTime();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += i;
        }
        long stringTime = System.nanoTime() - start;

        // StringBuilder
        start = System.nanoTime();
        StringBuilder sbResult = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sbResult.append(i);
        }
        long sbTime = System.nanoTime() - start;

        // StringBuffer
        start = System.nanoTime();
        StringBuffer sbfResult = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbfResult.append(i);
        }
        long sbfTime = System.nanoTime() - start;

        System.out.println("String concatenation: " + stringTime / 1_000_000 + " ms");
        System.out.println("StringBuilder: " + sbTime / 1_000_000 + " ms");
        System.out.println("StringBuffer: " + sbfTime / 1_000_000 + " ms");
        System.out.println("StringBuilder is fastest!");
        System.out.println();
    }

    // ============================================================
    // STRING VS STRINGBUILDER VS STRINGBUFFER DETAILS
    // ============================================================
    /*
     * Comparison:
     *
     * Feature         | String    | StringBuilder | StringBuffer
     * ----------------|-----------|---------------|-------------
     * Mutability      | Immutable | Mutable       | Mutable
     * Thread Safety   | Yes       | No            | Yes
     * Performance     | Slow      | Fast          | Slower
     * Since           | Java 1    | Java 5        | Java 1
     * Synchronized    | N/A       | No            | Yes
     *
     * When to use:
     * - String: Fixed text, keys, constants
     * - StringBuilder: Single-threaded, mutable text
     * - StringBuffer: Multi-threaded, mutable text (rare)
     *
     * String concatenation (+):
     * - Compiler converts to StringBuilder
     * - In loops: creates new StringBuilder each iteration
     * - Use explicit StringBuilder in loops
     */
}
