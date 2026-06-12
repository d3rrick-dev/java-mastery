package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between transient and volatile?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson17_WhatIsTheDifferenceBetweenTransientAndVolatile {
    public static void main(String[] args) {
        System.out.println("=== TRANSIENT VS VOLATILE ===\n");
        System.out.println("transient:");
        System.out.println("  - Keyword for serialization");
        System.out.println("  - Field is NOT serialized");
        System.out.println("  - Used for sensitive data (passwords)");
        System.out.println("  - Default value after deserialization");
        System.out.println();
        System.out.println("volatile:");
        System.out.println("  - Keyword for memory visibility");
        System.out.println("  - Ensures visibility across threads");
        System.out.println("  - Used in multi-threaded contexts");
        System.out.println("  - No effect on serialization");
        System.out.println();
        System.out.println("transient: serialization concern");
        System.out.println("volatile: concurrency concern");
    }
}
