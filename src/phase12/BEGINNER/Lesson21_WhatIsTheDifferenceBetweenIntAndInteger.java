package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between int and Integer?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of primitives vs wrappers
 * - Shows knowledge of autoboxing
 * - Reveals awareness of null handling
 */

public class Lesson21_WhatIsTheDifferenceBetweenIntAndInteger {

    public static void main(String[] args) {
        System.out.println("=== INT VS INTEGER ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between int and Integer?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("INT (primitive):");
        System.out.println("  - Primitive data type");
        System.out.println("  - Stored on stack");
        System.out.println("  - Default value: 0");
        System.out.println("  - Cannot be null");
        System.out.println("  - Faster (no object overhead)");
        System.out.println();

        System.out.println("INTEGER (wrapper class):");
        System.out.println("  - Object wrapper for int");
        System.out.println("  - Stored on heap");
        System.out.println("  - Default value: null");
        System.out.println("  - Can be null");
        System.out.println("  - Slower (object creation, GC)");
        System.out.println();

        System.out.println("AUTOBOXING/UNBOXING:");
        System.out.println("  - Automatic conversion between int and Integer");
        System.out.println("  - int -> Integer: autoboxing");
        System.out.println("  - Integer -> int: unboxing");
        System.out.println("  - Introduced in Java 5");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        int primitive = 10;
        Integer wrapper = 10;  // Autoboxing

        System.out.println("primitive = " + primitive);
        System.out.println("wrapper = " + wrapper);
        System.out.println("primitive == wrapper: " + (primitive == wrapper));  // true (unboxing)

        Integer a = 127;
        Integer b = 127;
        System.out.println("a == b (127): " + (a == b));  // true (cached)

        Integer c = 128;
        Integer d = 128;
        System.out.println("c == d (128): " + (c == d));  // false (not cached)
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using == to compare Integer objects:");
        System.out.println("   - Compares references, not values");
        System.out.println("   - Use equals() for values");
        System.out.println();

        System.out.println("2. Not knowing about caching:");
        System.out.println("   - Integer caches -128 to 127");
        System.out.println("   - Outside range, new objects created");
        System.out.println();

        System.out.println("3. NullPointerException with unboxing:");
        System.out.println("   - Integer x = null;");
        System.out.println("   - int y = x;  // NullPointerException!");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is autoboxing?");
        System.out.println("2. Why does Integer cache -128 to 127?");
        System.out.println("3. What happens when you compare Integer with ==?");
        System.out.println("4. When should you use int vs Integer?");
        System.out.println("5. What is the difference between == and equals() for wrappers?");
    }
}
