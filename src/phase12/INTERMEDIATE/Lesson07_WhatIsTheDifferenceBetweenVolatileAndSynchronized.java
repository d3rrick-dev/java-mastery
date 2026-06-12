package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between volatile and synchronized?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson07_WhatIsTheDifferenceBetweenVolatileAndSynchronized {
    public static void main(String[] args) {
        System.out.println("=== VOLATILE VS SYNCHRONIZED ===\n");
        System.out.println("volatile:");
        System.out.println("  - Keyword for variable visibility");
        System.out.println("  - Ensures visibility across threads");
        System.out.println("  - No atomicity guarantee (except single read/write)");
        System.out.println("  - No blocking/locking");
        System.out.println("  - Lighter weight");
        System.out.println();
        System.out.println("synchronized:");
        System.out.println("  - Keyword for mutual exclusion");
        System.out.println("  - Ensures visibility AND atomicity");
        System.out.println("  - Blocks other threads (locking)");
        System.out.println("  - Heavier weight");
        System.out.println("  - Can cause contention");
        System.out.println();
        System.out.println("Use volatile for: Flags, single-read/write variables");
        System.out.println("Use synchronized for: Compound operations, critical sections");
    }
}
