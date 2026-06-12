package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between String and StringBuffer?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson36_WhatIsTheDifferenceBetweenStringAndStringBuffer {
    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUFFER ===\n");
        System.out.println("String:");
        System.out.println("  - Immutable (cannot be changed)");
        System.out.println("  - Thread-safe (immutable = inherently safe)");
        System.out.println("  - Slower for concatenation (creates new objects)");
        System.out.println();
        System.out.println("StringBuffer:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Faster for concatenation (modifies in place)");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println();
        System.out.println("Use String for constants, StringBuffer for mutable thread-safe strings");
    }
}
