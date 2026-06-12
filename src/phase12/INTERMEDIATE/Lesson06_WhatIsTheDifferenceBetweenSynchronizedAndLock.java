package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between synchronized and Lock?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson06_WhatIsTheDifferenceBetweenSynchronizedAndLock {
    public static void main(String[] args) {
        System.out.println("=== SYNCHRONIZED VS LOCK ===\n");
        System.out.println("synchronized:");
        System.out.println("  - Keyword, built-in Java feature");
        System.out.println("  - Automatic lock release (JVM handles)");
        System.out.println("  - Block-level scope only");
        System.out.println("  - No fairness guarantee");
        System.out.println("  - Cannot interrupt waiting thread");
        System.out.println();
        System.out.println("Lock (ReentrantLock):");
        System.out.println("  - Interface (java.util.concurrent.locks.Lock)");
        System.out.println("  - Manual lock/unlock (try-finally required)");
        System.out.println("  - More flexible (tryLock, lockInterruptibly)");
        System.out.println("  - Fairness policy available");
        System.out.println("  - Supports condition variables");
        System.out.println();
        System.out.println("Use synchronized for simple cases");
        System.out.println("Use Lock for advanced features (fairness, interruptibility, tryLock)");
    }
}
