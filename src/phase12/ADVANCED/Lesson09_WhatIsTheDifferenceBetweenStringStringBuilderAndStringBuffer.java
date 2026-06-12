package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between String, StringBuilder, and StringBuffer?
 *
 * Difficulty: ADVANCED
 */

public class Lesson09_WhatIsTheDifferenceBetweenStringStringBuilderAndStringBuffer {
    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUILDER VS STRINGBUFFER ===\n");
        System.out.println("String:");
        System.out.println("  - Immutable (cannot be changed after creation)");
        System.out.println("  - Thread-safe (immutable = inherently thread-safe)");
        System.out.println("  - String pool for memory optimization");
        System.out.println("  - Concatenation creates new objects (inefficient in loops)");
        System.out.println();
        System.out.println("StringBuilder:");
        System.out.println("  - Mutable (can be modified in place)");
        System.out.println("  - NOT thread-safe");
        System.out.println("  - Faster (no synchronization overhead)");
        System.out.println("  - Introduced in Java 5");
        System.out.println("  - Preferred for single-threaded string manipulation");
        System.out.println();
        System.out.println("StringBuffer:");
        System.out.println("  - Mutable (can be modified in place)");
        System.out.println("  - Thread-safe (all methods synchronized)");
        System.out.println("  - Slower (synchronization overhead)");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println("  - Use only when thread safety is needed");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - String: immutable, thread-safe, string pool");
        System.out.println("  - StringBuilder: mutable, not thread-safe, faster");
        System.out.println("  - StringBuffer: mutable, thread-safe, slower");
    }
}
