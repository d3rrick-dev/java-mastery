package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between String, StringBuilder, and StringBuffer?
 *
 * Difficulty: BEGINNER
 */

public class Lesson43_WhatIsTheDifferenceBetweenStringStringBuilderAndStringBuffer {
    public static void main(String[] args) {
        System.out.println("=== STRING VS STRINGBUILDER VS STRINGBUFFER ===\n");
        System.out.println("String:");
        System.out.println("  - Immutable (cannot be changed)");
        System.out.println("  - Thread-safe (immutable = inherently safe)");
        System.out.println("  - Slower for concatenation (creates new objects)");
        System.out.println();
        System.out.println("StringBuilder:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - NOT thread-safe");
        System.out.println("  - Faster (no synchronization overhead)");
        System.out.println("  - Introduced in Java 5");
        System.out.println();
        System.out.println("StringBuffer:");
        System.out.println("  - Mutable (can be changed)");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Slower than StringBuilder");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println();
        System.out.println("Use String for constants, StringBuilder for single-threaded, StringBuffer for multi-threaded");
    }
}
