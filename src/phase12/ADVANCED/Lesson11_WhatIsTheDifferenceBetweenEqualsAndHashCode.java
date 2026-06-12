package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between equals() and hashCode()?
 *
 * Difficulty: ADVANCED
 */

public class Lesson11_WhatIsTheDifferenceBetweenEqualsAndHashCode {
    public static void main(String[] args) {
        System.out.println("=== EQUALS() VS HASHCODE() ===\n");
        System.out.println("equals():");
        System.out.println("  - Method from Object class");
        System.out.println("  - Compares content/state of objects");
        System.out.println("  - Default: reference equality (==)");
        System.out.println("  - Should be overridden for logical equality");
        System.out.println("  - Contract: reflexive, symmetric, transitive, consistent, non-null");
        System.out.println();
        System.out.println("hashCode():");
        System.out.println("  - Method from Object class");
        System.out.println("  - Returns integer hash value");
        System.out.println("  - Used in hash-based collections (HashMap, HashSet)");
        System.out.println("  - Contract: equal objects must have same hashCode");
        System.out.println("  - Unequal objects CAN have same hashCode (collision)");
        System.out.println();
        System.out.println("Critical Rule:");
        System.out.println("  - If you override equals(), you MUST override hashCode()");
        System.out.println("  - Otherwise, HashMap/HashSet will not work correctly");
        System.out.println("  - Example: Two equal objects with different hashCodes won't be found in HashSet");
    }
}
