package phase7;

/**
 * LESSON 12: SYNCHRONIZATION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Synchronization controls access to shared resources by multiple threads.
 * It ensures that only one thread can access a critical section at a time.
 * Think of it like a bathroom key - only one person can use it at a time.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Multiple threads can corrupt shared data
 * - Need to prevent race conditions
 * - Need to maintain data consistency
 * - Need to coordinate thread execution
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Bank account:
 * - Husband and wife share account
 * - Both try to withdraw at same time
 * - Without sync: balance goes negative
 * - With sync: one at a time, balance stays correct
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Connection pool:
 * - Multiple threads request connections
 * - Without sync: two threads get same connection
 * - With sync: one thread gets connection at a time
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is synchronization?"
 * Answer: Mechanism to control thread access to shared resources,
 *         prevents race conditions
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Synchronizing on wrong object (this vs private lock)
 * - Synchronizing too much (performance hit)
 * - Synchronizing too little (race conditions)
 * - Nested synchronization (deadlock risk)
 * - Using String/interned objects as locks
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - synchronized adds overhead (~50-100ns per call)
 * - Contention causes threads to wait
 * - Fine-grained locking is better than coarse
 * - ReadWriteLock allows concurrent reads
 *
 * ============================================================
 * SYNCHRONIZATION MECHANISMS
 * ============================================================
 *
 *   1. synchronized method
 *   2. synchronized block
 *   3. ReentrantLock
 *   4. ReadWriteLock
 *   5. Semaphore
 *   6. CountDownLatch
 *   7. CyclicBarrier
 *   8. Phaser
 */

public class Lesson12_Synchronization {

    private int counter = 0;
    private final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== SYNCHRONIZATION ===\n");

        Lesson12_Synchronization demo = new Lesson12_Synchronization();

        // ============================================================
        // EXAMPLE 1: Unsynchronized (race condition)
        // ============================================================
        System.out.println("--- Example 1: Without Synchronization ---\n");

        UnsafeCounter unsafe = new UnsafeCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafe.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafe.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Unsafe count: " + unsafe.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 2: synchronized method
        // ============================================================
        System.out.println("--- Example 2: synchronized Method ---\n");

        SafeCounter safeMethod = new SafeCounter();

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safeMethod.increment();
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safeMethod.increment();
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Safe method count: " + safeMethod.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 3: synchronized block
        // ============================================================
        System.out.println("--- Example 3: synchronized Block ---\n");

        SafeCounterBlock safeBlock = new SafeCounterBlock();

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safeBlock.increment();
        });
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) safeBlock.increment();
        });

        t5.start();
        t6.start();
        t5.join();
        t6.join();

        System.out.println("Safe block count: " + safeBlock.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 4: Synchronized on different objects
        // ============================================================
        System.out.println("--- Example 4: Lock Object ---\n");

        LockObjectCounter lockCounter = new LockObjectCounter();

        Thread t7 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) lockCounter.increment();
        });
        Thread t8 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) lockCounter.increment();
        });

        t7.start();
        t8.start();
        t7.join();
        t8.join();

        System.out.println("Lock object count: " + lockCounter.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 5: Static synchronization
        // ============================================================
        System.out.println("--- Example 5: Static Synchronization ---\n");

        StaticCounter staticCounter = new StaticCounter();

        Thread t9 = new Thread(() -> {
            for (int i = 0; i < 500; i++) StaticCounter.incrementStatic();
        });
        Thread t10 = new Thread(() -> {
            for (int i = 0; i < 500; i++) StaticCounter.incrementStatic();
        });

        t9.start();
        t10.start();
        t9.join();
        t10.join();

        System.out.println("Static count: " + StaticCounter.getStaticCount() + " (expected 1000)\n");

        // ============================================================
        // EXAMPLE 6: Synchronized on class object
        // ============================================================
        System.out.println("--- Example 6: Class-level Lock ---\n");

        ClassLockCounter classLock = new ClassLockCounter();

        Thread t11 = new Thread(() -> {
            for (int i = 0; i < 500; i++) classLock.increment();
        });
        Thread t12 = new Thread(() -> {
            for (int i = 0; i < 500; i++) classLock.increment();
        });

        t11.start();
        t12.start();
        t11.join();
        t12.join();

        System.out.println("Class lock count: " + classLock.getCount() + " (expected 1000)\n");

        // ============================================================
        // EXAMPLE 7: Reentrant behavior
        // ============================================================
        System.out.println("--- Example 7: Reentrant Lock ---\n");

        ReentrantDemo reentrant = new ReentrantDemo();
        reentrant.outer();
        System.out.println();

        // ============================================================
        // EXAMPLE 8: Common synchronization mistakes
        // ============================================================
        System.out.println("--- Example 8: Common Mistakes ---\n");

        System.out.println("Mistake 1: Synchronizing on String literal");
        System.out.println("  String lock = \"lock\";  // WRONG - interned, shared!");
        System.out.println("  Use: new Object() instead\n");

        System.out.println("Mistake 2: Synchronizing on this");
        System.out.println("  public synchronized void method()  // Exposes lock!");
        System.out.println("  Use: private final Object lock = new Object()\n");

        System.out.println("Mistake 3: Synchronizing getters");
        System.out.println("  public synchronized int get()  // Unnecessary!");
        System.out.println("  Only sync methods that MODIFY state\n");

        System.out.println("Mistake 4: Double-checked locking without volatile");
        System.out.println("  if (instance == null) { synchronized... }  // BROKEN!");
        System.out.println("  Use: volatile + synchronized\n");
    }

    // ============================================================
    // EXAMPLE CLASSES
    // ============================================================

    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++;  // NOT thread-safe!
        }

        public int getCount() {
            return count;
        }
    }

    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    static class SafeCounterBlock {
        private int count = 0;

        public void increment() {
            synchronized (this) {
                count++;
            }
        }

        public int getCount() {
            synchronized (this) {
                return count;
            }
        }
    }

    static class LockObjectCounter {
        private int count = 0;
        private final Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }

    static class StaticCounter {
        private static int staticCount = 0;

        public static synchronized void incrementStatic() {
            staticCount++;
        }

        public static synchronized int getStaticCount() {
            return staticCount;
        }
    }

    static class ClassLockCounter {
        private int count = 0;

        public void increment() {
            synchronized (ClassLockCounter.class) {
                count++;
            }
        }

        public int getCount() {
            synchronized (ClassLockCounter.class) {
                return count;
            }
        }
    }

    static class ReentrantDemo {
        private final Object lock = new Object();

        public void outer() {
            synchronized (lock) {
                System.out.println("In outer method");
                inner();  // Can re-enter same lock
            }
        }

        public void inner() {
            synchronized (lock) {
                System.out.println("In inner method (re-entered)");
            }
        }
    }
}
