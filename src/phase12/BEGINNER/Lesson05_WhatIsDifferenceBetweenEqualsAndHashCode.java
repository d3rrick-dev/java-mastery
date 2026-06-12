package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between equals() and hashCode()?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of object equality
 * - Shows knowledge of HashMap/HashSet internals
 * - Reveals understanding of contracts
 */

public class Lesson05_WhatIsDifferenceBetweenEqualsAndHashCode {

    public static void main(String[] args) {
        System.out.println("=== EQUALS() VS HASHCODE() ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between equals() and hashCode()?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("equals():");
        System.out.println("  - Checks logical equality of objects");
        System.out.println("  - Returns true if objects are equal");
        System.out.println("  - Can be overridden in any class");
        System.out.println("  - Used by HashMap, HashSet for comparison");
        System.out.println();

        System.out.println("hashCode():");
        System.out.println("  - Returns integer hash value");
        System.out.println("  - Used to determine bucket in hash-based collections");
        System.out.println("  - Should be consistent during object lifetime");
        System.out.println("  - Equal objects MUST have same hashCode");
        System.out.println();

        System.out.println("CONTRACT:");
        System.out.println("  1. If equals() is true, hashCode() must be same");
        System.out.println("  2. If hashCode() is same, equals() may be false (collision)");
        System.out.println("  3. hashCode() must return same value for same object");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        String s1 = "hello";
        String s2 = new String("hello");

        System.out.println("s1.equals(s2): " + s1.equals(s2));  // true
        System.out.println("s1.hashCode(): " + s1.hashCode());
        System.out.println("s2.hashCode(): " + s2.hashCode());
        System.out.println("Same hashCode: " + (s1.hashCode() == s2.hashCode()));
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Overriding equals() but not hashCode():");
        System.out.println("   - Breaks HashMap/HashSet contract");
        System.out.println("   - Equal objects in different buckets");
        System.out.println();

        System.out.println("2. Using == for object comparison:");
        System.out.println("   - == compares references, not content");
        System.out.println("   - Use equals() for logical equality");
        System.out.println();

        System.out.println("3. Mutable hashCode:");
        System.out.println("   - If hashCode changes, object lost in HashMap");
        System.out.println("   - Use immutable fields for hashCode");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What happens if you break the equals/hashCode contract?");
        System.out.println("2. How does HashMap use hashCode internally?");
        System.out.println("3. What is a hash collision?");
        System.out.println("4. How to implement equals() and hashCode() correctly?");
        System.out.println("5. What is the difference between == and equals()?");
    }
}
