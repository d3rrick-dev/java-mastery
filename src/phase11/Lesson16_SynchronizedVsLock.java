package phase11;

import java.util.concurrent.locks.*;

/**
 * LESSON 16: SYNCHRONIZED VS LOCK
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * synchronized: Built-in Java keyword for locking
 * Lock: Explicit lock interface (ReentrantLock)
 * Like automatic door lock vs smart lock with more features.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - synchronized: Simple, automatic lock management
 * - Lock: More control, tryLock, fairness, interruptible
 */

public class Lesson16_SynchronizedVsLock {

    public static void main(String[] args) {
        System.out.println("=== SYNCHRONIZED VS LOCK ===\n");

        // ============================================================
        // EXAMPLE 1: synchronized
        // ============================================================
        System.out.println("--- Example 1: synchronized ---\n");

        System.out.println("synchronized keyword:");
        System.out.println("  - Implicit lock acquisition/release");
        System.out.println("  - Lock released automatically on exception");
        System.out.println("  - Only one way to acquire: blocking");
        System.out.println("  - Non-fair (no ordering guarantee)");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  public synchronized void method() { ... }");
        System.out.println("  synchronized (lock) { ... }");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Lock interface
        // ============================================================
        System.out.println("--- Example 2: Lock Interface ---\n");

        Lock lock = new ReentrantLock();

        System.out.println("Lock interface features:");
        System.out.println("  - Explicit lock()/unlock() calls");
        System.out.println("  - tryLock(): Non-blocking attempt");
        System.out.println("  - lockInterruptibly(): Interruptible lock");
        System.out.println("  - Fairness policy (FIFO ordering)");
        System.out.println("  - Multiple Condition objects");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  lock.lock();");
        System.out.println("  try {");
        System.out.println("    // critical section");
        System.out.println("  } finally {");
        System.out.println("    lock.unlock();  // Must release manually!");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: tryLock() demonstration
        // ============================================================
        System.out.println("--- Example 3: tryLock() ---\n");

        Lock tryLock = new ReentrantLock();

        boolean acquired = tryLock.tryLock();
        System.out.println("First tryLock: " + acquired);

        if (acquired) {
            try {
                boolean secondTry = tryLock.tryLock();
                System.out.println("Second tryLock (same thread): " + secondTry);
                System.out.println("ReentrantLock allows reentrant locking");
            } finally {
                tryLock.unlock();
            }
        }

        tryLock.unlock();  // Release first lock
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Fair vs Non-fair
        // ============================================================
        System.out.println("--- Example 4: Fair vs Non-fair ---\n");

        Lock fairLock = new ReentrantLock(true);   // Fair
        Lock nonFairLock = new ReentrantLock(false); // Non-fair

        System.out.println("Fair Lock (true):");
        System.out.println("  - FIFO ordering (longest-waiting thread first)");
        System.out.println("  - Higher overhead");
        System.out.println("  - Prevents starvation");
        System.out.println();
        System.out.println("Non-fair Lock (false, default):");
        System.out.println("  - No ordering guarantee");
        System.out.println("  - Lower overhead");
        System.out.println("  - Higher throughput");
        System.out.println();
    }

    // ============================================================
    // SYNCHRONIZED VS LOCK DETAILS
    // ============================================================
    /*
     * Comparison:
     *
     * Feature              | synchronized | Lock
     * ---------------------|--------------|------
     * Acquisition          | Implicit     | Explicit
     * Release              | Automatic    | Manual (finally)
     * Try lock             | No           | Yes (tryLock)
     * Interruptible        | No           | Yes (lockInterruptibly)
     * Fairness             | No           | Yes (configurable)
     * Multiple conditions  | No (1 monitor) | Yes (newCondition())
     * Performance          | Good         | Better (contention)
     *
     * When to use synchronized:
     * - Simple locking
     * - No special requirements
     * - Automatic release preferred
     *
     * When to use Lock:
     * - Need tryLock()
     * - Need interruptible locking
     * - Need multiple conditions
     * - Need fairness
     */
}
