package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between equals() and ==?
 *
 * Difficulty: BEGINNER
 */

public class Lesson34_WhatIsTheDifferenceBetweenEqualsAndDoubleEquals {
    public static void main(String[] args) {
        System.out.println("=== EQUALS() VS == ===\n");
        System.out.println("== (double equals):");
        System.out.println("  - Compares references (memory addresses)");
        System.out.println("  - For primitives: compares values");
        System.out.println("  - For objects: compares if same object");
        System.out.println();
        System.out.println("equals():");
        System.out.println("  - Compares content/values");
        System.out.println("  - Default: same as == (from Object class)");
        System.out.println("  - Overridden in String, Integer, etc.");
        System.out.println();
        System.out.println("String pool: == may work for literals, equals() always works");
    }
}
