package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between HashSet and TreeSet?
 *
 * Difficulty: BEGINNER
 */

public class Lesson38_WhatIsTheDifferenceBetweenHashSetAndTreeSet {
    public static void main(String[] args) {
        System.out.println("=== HASHSET VS TREESET ===\n");
        System.out.println("HashSet:");
        System.out.println("  - Backed by HashMap");
        System.out.println("  - No ordering guaranteed");
        System.out.println("  - O(1) add, remove, contains");
        System.out.println("  - Allows null elements");
        System.out.println();
        System.out.println("TreeSet:");
        System.out.println("  - Backed by TreeMap (Red-Black tree)");
        System.out.println("  - Natural ordering (sorted)");
        System.out.println("  - O(log n) add, remove, contains");
        System.out.println("  - Does NOT allow null elements");
        System.out.println();
        System.out.println("Use HashSet for fast lookup, TreeSet for sorted data");
    }
}
