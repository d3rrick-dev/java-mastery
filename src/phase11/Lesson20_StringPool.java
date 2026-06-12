package phase11;

/**
 * LESSON 20: STRING POOL
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * String Pool (String Intern Pool) is a special memory area
 * where String literals are stored and reused.
 * Like a library of pre-made strings to save memory.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Save memory (reuse common strings)
 * - Faster string comparison (== works for literals)
 * - Efficient string handling
 */

public class Lesson20_StringPool {

    public static void main(String[] args) {
        System.out.println("=== STRING POOL ===\n");

        // ============================================================
        // EXAMPLE 1: String interning
        // ============================================================
        System.out.println("--- Example 1: String Interning ---\n");

        String s1 = "hello";      // Goes to pool
        String s2 = "hello";      // Reuses from pool
        String s3 = new String("hello");  // New object, NOT from pool

        System.out.println("s1 == s2: " + (s1 == s2));  // true (same pool object)
        System.out.println("s1 == s3: " + (s1 == s3));  // false (different objects)
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // true (same content)
        System.out.println();

        // ============================================================
        // EXAMPLE 2: intern() method
        // ============================================================
        System.out.println("--- Example 2: intern() Method ---\n");

        String s4 = new String("world");
        String s5 = s4.intern();  // Add to pool if not present
        String s6 = "world";      // From pool

        System.out.println("s4 == s5: " + (s4 == s5));  // false (s4 is new object)
        System.out.println("s5 == s6: " + (s5 == s6));  // true (both from pool)
        System.out.println();

        // ============================================================
        // EXAMPLE 3: String pool location
        // ============================================================
        System.out.println("--- Example 3: String Pool Location ---\n");

        System.out.println("Java 7 and earlier:");
        System.out.println("  - String pool in PermGen (fixed size)");
        System.out.println("  - Could cause OutOfMemoryError: PermGen space");
        System.out.println();
        System.out.println("Java 7+:");
        System.out.println("  - String pool in Heap");
        System.out.println("  - Same garbage collection as other objects");
        System.out.println("  - Can grow dynamically");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: When strings are interned
        // ============================================================
        System.out.println("--- Example 4: When Strings Are Interned ---\n");

        System.out.println("Automatically interned:");
        System.out.println("  - String literals: \"hello\"");
        System.out.println("  - String constants in code");
        System.out.println();
        System.out.println("Manually interned:");
        System.out.println("  - new String(\"hello\").intern()");
        System.out.println("  - String created at runtime");
        System.out.println();
        System.out.println("NOT interned:");
        System.out.println("  - new String(\"hello\") without intern()");
        System.out.println("  - StringBuilder.toString()");
        System.out.println();
    }

    // ============================================================
    // STRING POOL DETAILS
    // ============================================================
    /*
     * String Pool:
     *
     * 1. What is it?
     *    - Special heap area for String literals
     *    - Stores unique string values
     *    - Reuses existing strings
     *
     * 2. How does it work?
     *    - String literals auto-interned
     *    - intern() adds to pool
     *    - == compares references (works for pool strings)
     *
     * 3. Benefits:
     *    - Memory savings (reuse)
     *    - Fast comparison (== for literals)
     *    - Efficient storage
     *
     * 4. Location:
     *    - Java 6: PermGen
     *    - Java 7+: Heap (part of Young Gen)
     *
     * 5. Garbage Collection:
     *    - Java 7+: Pool strings GC'd like other objects
     *    - No more PermGen OOM from strings
     */
}
