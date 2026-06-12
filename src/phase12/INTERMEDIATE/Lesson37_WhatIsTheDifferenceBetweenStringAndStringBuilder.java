package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between String and StringBuilder?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson37_WhatIsTheDifferenceBetweenStringAndStringBuilder {
    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUILDER ===\n");
        System.out.println("String:");
        System.out.println("  - Immutable (cannot be changed)");
        System.out.println("  - Thread-safe (immutable = inherently safe)");
        System.out.println("  - Slower for concatenation (creates new objects)");
        System.out.println();
        System.out.println("StringBuilder:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - NOT thread-safe");
        System.out.println("  - Faster for concatenation (modifies in place)");
        System.out.println("  - Introduced in Java 5");
        System.out.println();
        System.out.println("Use String for constants, StringBuilder for single-threaded mutable strings");
    }
}
