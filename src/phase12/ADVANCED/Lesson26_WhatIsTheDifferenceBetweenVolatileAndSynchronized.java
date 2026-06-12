package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between volatile and synchronized?
 *
 * Difficulty: ADVANCED
 */

public class Lesson26_WhatIsTheDifferenceBetweenVolatileAndSynchronized {
    public static void main(String[] args) {
        System.out.println("=== VOLATILE VS SYNCHRONIZED ===\n");
        System.out.println("volatile:");
        System.out.println("  - Keyword for variables only");
        System.out.println("  - Ensures visibility across threads");
        System.out.println("  - Prevents instruction reordering");
        System.out.println("  - No locking (lightweight)");
        System.out.println("  - Only guarantees visibility, NOT atomicity");
        System.out.println("  - Example: boolean flag = volatile");
        System.out.println();
        System.out.println("synchronized:");
        System.out.println("  - Keyword for methods or blocks");
        System.out.println("  - Ensures mutual exclusion (only one thread at a time)");
        System.out.println("  - Ensures visibility and atomicity");
        System.out.println("  - Uses locking (heavier)");
        System.out.println("  - Can cause deadlocks if misused");
        System.out.println("  - Example: synchronized void method()");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Scope: variable vs method/block");
        System.out.println("  - Atomicity: NOT guaranteed vs guaranteed");
        System.out.println("  - Performance: lightweight vs heavyweight");
        System.out.println("  - Blocking: no blocking vs thread blocking");
        System.out.println();
        System.out.println("When to use:");
        System.out.println("  - volatile: simple flags, single-writer scenarios");
        System.out.println("  - synchronized: compound operations, critical sections");
    }
}
