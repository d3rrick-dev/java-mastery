package phase7;

/**
 * LESSON 23: THREAD SAFETY
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Thread safety means a class or method behaves correctly when
 * accessed by multiple threads simultaneously. No race conditions,
 * no data corruption, no visibility issues.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Multi-threaded applications are common
 * - Shared data can be corrupted without protection
 * - Need predictable behavior under concurrency
 * - Prevent subtle bugs that appear only under load
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Bank account:
 * - Thread-safe: Balance always correct
 * - Not thread-safe: Balance can go negative
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * User session cache:
 * - Thread-safe: All threads see consistent data
 * - Not thread-safe: Users see other users' data
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is thread safety?"
 * Answer: Correct behavior under concurrent access,
 *         no race conditions or data corruption
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Assuming immutable = thread-safe (it is, but not vice versa)
 * - Not understanding publication safety
 * - Using thread-local when you need shared state
 * - Forgetting about instruction reordering
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Thread-safe code often has synchronization overhead
 * - Immutability is free thread-safety
 * - Fine-grained locking improves concurrency
 * - Lock-free algorithms are fastest but complex
 *
 * ============================================================
 * THREAD SAFETY LEVELS
 * ============================================================
 *
 *   Immutable          Thread-safe       Conditionally
 *   +-------------+    +-------------+    +-------------+
 *   | Always safe |    | Safe with   |    | Safe only   |
 *   | No sync     |    | sync        |    | in specific |
 *   | No sharing  |    | Shared      |    | contexts    |
 *   +-------------+    +-------------+    +-------------+
 *   | String      |    | Vector      |    | SimpleDateFormat|
 *   +-------------+    +-------------+    +-------------+
 */

public class Lesson23_ThreadSafety {

    public static void main(String[] args) throws Exception {
        System.out.println("=== THREAD SAFETY ===\n");

        // ============================================================
        // EXAMPLE 1: Thread-safe class
        // ============================================================
        System.out.println("--- Example 1: Thread-Safe Class ---\n");

        ThreadSafeCounter safeCounter = new ThreadSafeCounter();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    safeCounter.increment();
                }
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Thread-safe count: " + safeCounter.getCount() + " (expected 1000)\n");

        // ============================================================
        // EXAMPLE 2: Not thread-safe class
        // ============================================================
        System.out.println("--- Example 2: Not Thread-Safe ---\n");

        UnsafeCounter unsafeCounter = new UnsafeCounter();

        Thread[] unsafeThreads = new Thread[10];
        for (int i = 0; i < unsafeThreads.length; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    unsafeCounter.increment();
                }
            });
        }

        for (Thread t : unsafeThreads) t.start();
        for (Thread t : unsafeThreads) t.join();

        System.out.println("Unsafe count: " + unsafeCounter.getCount() + " (expected 1000, may be less)\n");

        // ============================================================
        // EXAMPLE 3: Thread confinement
        // ============================================================
        System.out.println("--- Example 3: Thread Confinement ---\n");

        ThreadLocalCounter threadLocalCounter = new ThreadLocalCounter();

        Thread[] localThreads = new Thread[3];
        for (int i = 0; i < localThreads.length; i++) {
            final int id = i;
            localThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    threadLocalCounter.increment();
                }
                System.out.println("Thread-" + id + " count: " + threadLocalCounter.get());
            });
        }

        for (Thread t : localThreads) t.start();
        for (Thread t : localThreads) t.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Immutability = thread safety
        // ============================================================
        System.out.println("--- Example 4: Immutability ---\n");

        ImmutablePoint point = new ImmutablePoint(10, 20);
        System.out.println("Point: " + point);

        // Multiple threads can safely read
        Thread[] readers = new Thread[5];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(() -> {
                System.out.println("Thread sees: " + point);
            });
        }

        for (Thread t : readers) t.start();
        for (Thread t : readers) t.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Safe publication
        // ============================================================
        System.out.println("--- Example 5: Safe Publication ---\n");

        // Unsafe publication
        System.out.println("Unsafe publication (may see partially constructed object):");
        Holder holder = new Holder(42);
        new Thread(() -> {
            System.out.println("Reader sees: " + holder.value);
        }).start();

        // Safe publication with volatile
        SafeHolder safeHolder = new SafeHolder(42);
        new Thread(() -> {
            System.out.println("Safe reader sees: " + safeHolder.value);
        }).start();

        Thread.sleep(100);
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Thread safety checklist
        // ============================================================
        System.out.println("--- Example 6: Thread Safety Checklist ---\n");

        System.out.println("To make a class thread-safe:");
        System.out.println("1. Make it immutable (best option)");
        System.out.println("2. Use synchronization (synchronized, Lock)");
        System.out.println("3. Use atomic variables (AtomicInteger, etc.)");
        System.out.println("4. Use concurrent collections");
        System.out.println("5. Use thread confinement (ThreadLocal)");
        System.out.println("6. Ensure safe publication");
        System.out.println();
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class ThreadSafeCounter {
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

    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++;  // NOT thread-safe!
        }

        public int getCount() {
            return count;
        }
    }

    static class ThreadLocalCounter {
        private final ThreadLocal<Integer> threadCount =
            ThreadLocal.withInitial(() -> 0);

        public void increment() {
            threadCount.set(threadCount.get() + 1);
        }

        public int get() {
            return threadCount.get();
        }
    }

    static class ImmutablePoint {
        private final int x;
        private final int y;

        public ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    static class Holder {
        public int value;

        public Holder(int value) {
            this.value = value;
        }
    }

    static class SafeHolder {
        public volatile int value;

        public SafeHolder(int value) {
            this.value = value;
        }
    }
}
