package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Enumeration and Iterator?
 *
 * Difficulty: ADVANCED
 */

public class Lesson31_WhatIsTheDifferenceBetweenEnumerationAndIterator {
    public static void main(String[] args) {
        System.out.println("=== ENUMERATION VS ITERATOR ===\n");
        System.out.println("Enumeration:");
        System.out.println("  - Legacy interface (Java 1.0)");
        System.out.println("  - Used by legacy classes (Vector, Hashtable)");
        System.out.println("  - Methods: hasMoreElements(), nextElement()");
        System.out.println("  - Read-only (no remove() method)");
        System.out.println("  - Not fail-fast");
        System.out.println();
        System.out.println("Iterator:");
        System.out.println("  - Modern interface (Java 1.2)");
        System.out.println("  - Used by all Collection classes");
        System.out.println("  - Methods: hasNext(), next(), remove()");
        System.out.println("  - Can remove elements during iteration");
        System.out.println("  - Fail-fast (throws ConcurrentModificationException)");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Age: legacy vs modern");
        System.out.println("  - Removal: not supported vs supported");
        System.out.println("  - Fail-fast: no vs yes");
        System.out.println("  - Usage: Vector/Hashtable vs all collections");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Always prefer Iterator over Enumeration");
        System.out.println("  - Enumeration is kept only for backward compatibility");
    }
}
