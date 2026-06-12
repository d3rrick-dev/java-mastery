package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between equals() and hashCode()?
 *
 * Difficulty: BEGINNER
 */

public class Lesson50_WhatIsTheDifferenceBetweenEqualsAndHashCode {
    public static void main(String[] args) {
        System.out.println("=== EQUALS() VS HASHCODE() ===\n");
        System.out.println("equals():");
        System.out.println("  - Compares content/values of objects");
        System.out.println("  - Returns boolean");
        System.out.println("  - Default: compares references (from Object class)");
        System.out.println("  - Must be overridden for logical equality");
        System.out.println();
        System.out.println("hashCode():");
        System.out.println("  - Returns integer hash code for object");
        System.out.println("  - Used in hash-based collections (HashMap, HashSet)");
        System.out.println("  - Equal objects MUST have same hashCode");
        System.out.println("  - Unequal objects CAN have same hashCode (collision)");
        System.out.println();
        System.out.println("Contract: If a.equals(b) is true, then a.hashCode() == b.hashCode()");
    }
}
