package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between StringBuffer and StringBuilder?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson18_WhatIsTheDifferenceBetweenStringBufferAndStringBuilder {
    public static void main(String[] args) {
        System.out.println("=== STRINGBUFFER VS STRINGBUILDER ===\n");
        System.out.println("StringBuffer:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Slower due to synchronization overhead");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println();
        System.out.println("StringBuilder:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - NOT thread-safe");
        System.out.println("  - Faster (no synchronization)");
        System.out.println("  - Introduced in Java 5");
        System.out.println();
        System.out.println("Use StringBuilder for single-threaded (most cases)");
        System.out.println("Use StringBuffer only when thread-safety required");
    }
}
