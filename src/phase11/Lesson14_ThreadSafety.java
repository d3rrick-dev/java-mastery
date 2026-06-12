package phase11;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LESSON 14: THREAD SAFETY
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Thread safety means code works correctly when accessed
 * by multiple threads simultaneously. Like a shared notebook
 * where everyone can write without overwriting each other.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Prevent data corruption
 * - Ensure consistent behavior
 * - Avoid race conditions
 */

public class Lesson14_ThreadSafety {

    public static void main(String[] args) {
        System.out.println("=== THREAD SAFETY ===\n");

        // ============================================================
        // EXAMPLE 1: Thread-unsafe code
        // ============================================================
        System.out.println("--- Example 1: Thread-Unsafe Counter ---\n");

        UnsafeCounter unsafe = new UnsafeCounter();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            executor.submit(unsafe::increment);
        }

        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}

        System.out.println("Unsafe counter result: " + unsafe.getCount());
        System.out.println("Expected: 1000");
        System.out.println("Race condition: count may be less than 1000");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Thread-safe with synchronized
        // ============================================================
        System.out.println("--- Example 2: Synchronized Counter ---\n");

        SafeCounter safe = new SafeCounter();
        executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            executor.submit(safe::increment);
        }

        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}

        System.out.println("Safe counter result: " + safe.getCount());
        System.out.println("Expected: 1000");
        System.out.println("Synchronized: always correct");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Thread-safe with AtomicInteger
        // ============================================================
        System.out.println("--- Example 3: Atomic Counter ---\n");

        AtomicCounter atomic = new AtomicCounter();
        executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            executor.submit(atomic::increment);
        }

        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}

        System.out.println("Atomic counter result: " + atomic.getCount());
        System.out.println("Expected: 1000");
        System.out.println("Atomic: lock-free, always correct");
        System.out.println();
    }

    // ============================================================
    // THREAD SAFETY EXAMPLES
    // ============================================================

    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++;  // Not atomic! Read-modify-write race
        }

        public int getCount() {
            return count;
        }
    }

    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;  // Atomic with synchronization
        }

        public synchronized int getCount() {
            return count;
        }
    }

    static class AtomicCounter {
        private final AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            count.incrementAndGet();  // Atomic operation
        }

        public int getCount() {
            return count.get();
        }
    }
}
