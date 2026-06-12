package phase7;

/**
 * LESSON 16: STARVATION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Starvation occurs when a thread is perpetually denied access to
 * resources it needs. Like a person who never gets served at a
 * restaurant because other people keep cutting in line.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Threads have different priorities
 * - Greedy threads hold locks too long
 * - Fairness not enforced in synchronization
 * - High-priority threads monopolize CPU
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Print queue:
 * - Boss's print job always goes first
 * - Employee jobs keep getting pushed back
 * - Employees never get their prints
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API rate limiting:
 * - VIP users get priority
 * - Regular users' requests keep timing out
 * - Regular users starve
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is thread starvation?"
 * Answer: When a thread is denied resources indefinitely,
 *         often due to priority or unfair scheduling
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using thread priorities (not guaranteed)
 * - Holding locks for too long
 * - Not using fair locks
 * - Infinite loops in high-priority threads
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Starved threads waste resources
 * - Application throughput decreases
 * - User experience degrades
 * - Fair locks have higher overhead
 *
 * ============================================================
 * STARVATION vs DEADLOCK vs LIVELOCK
 * ============================================================
 *
 *   DEADLOCK        STARVATION        LIVELOCK
 *   +--------+      +--------+        +--------+
 *   | Blocked |      | Waiting |       | Active |
 *   | No CPU  |      | No CPU  |       | 100%   |
 *   | Forever |      | Forever |       | No prog|
 *   +--------+      +--------+        +--------+
 */

public class Lesson16_Starvation {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== STARVATION ===\n");

        // ============================================================
        // EXAMPLE 1: Priority-based starvation
        // ============================================================
        System.out.println("--- Example 1: Priority Starvation ---\n");

        StarvationDemo demo = new StarvationDemo();

        // Low priority thread
        Thread lowPriority = new Thread(() -> {
            int count = 0;
            while (count < 20) {
                System.out.println("Low priority: " + count);
                count++;
                Thread.yield();  // Yield to other threads
            }
        }, "LowPriority");

        // High priority thread (greedy)
        Thread highPriority = new Thread(() -> {
            int count = 0;
            while (count < 100) {
                System.out.println("High priority: " + count);
                count++;
            }
        }, "HighPriority");

        lowPriority.setPriority(Thread.MIN_PRIORITY);
        highPriority.setPriority(Thread.MAX_PRIORITY);

        lowPriority.start();
        highPriority.start();

        lowPriority.join();
        highPriority.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Lock starvation
        // ============================================================
        System.out.println("--- Example 2: Lock Starvation ---\n");

        LockStarvation lockDemo = new LockStarvation();

        // Many threads competing for lock
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    lockDemo.doWork(id);
                }
            }, "Thread-" + i);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Fair lock solution
        // ============================================================
        System.out.println("--- Example 3: Fair Lock ---\n");

        FairLockDemo fairDemo = new FairLockDemo();

        Thread[] fairThreads = new Thread[5];
        for (int i = 0; i < fairThreads.length; i++) {
            final int id = i;
            fairThreads[i] = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    fairDemo.doWork(id);
                }
            }, "Fair-" + i);
        }

        for (Thread t : fairThreads) {
            t.start();
        }

        for (Thread t : fairThreads) {
            t.join();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Greedy thread causing starvation
        // ============================================================
        System.out.println("--- Example 4: Greedy Thread ---\n");

        GreedyThreadDemo greedyDemo = new GreedyThreadDemo();

        Thread greedy = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                greedyDemo.greedyWork();
            }
        }, "Greedy");

        Thread polite = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                greedyDemo.politeWork();
            }
        }, "Polite");

        greedy.start();
        polite.start();

        greedy.join();
        polite.join();

        System.out.println("Greedy work count: " + greedyDemo.getGreedyCount());
        System.out.println("Polite work count: " + greedyDemo.getPoliteCount());
        System.out.println("(Polite may have been starved)\n");

        // ============================================================
        // EXAMPLE 5: Prevention strategies
        // ============================================================
        System.out.println("--- Example 5: Prevention Strategies ---\n");

        System.out.println("1. Use Fair Locks:");
        System.out.println("   new ReentrantLock(true) // fair=true\n");

        System.out.println("2. Avoid Thread Priorities:");
        System.out.println("   Priorities are not guaranteed by JVM\n");

        System.out.println("3. Limit Lock Hold Time:");
        System.out.println("   Don't hold locks during I/O\n");

        System.out.println("4. Use tryLock:");
        System.out.println("   Give up if can't acquire immediately\n");

        System.out.println("5. Thread Pool Sizing:");
        System.out.println("   Ensure enough threads for workload\n");
    }

    // ============================================================
    // EXAMPLE CLASSES
    // ============================================================

    static class StarvationDemo {
        private int sharedResource = 0;

        public void doWork(int threadId) {
            synchronized (this) {
                sharedResource++;
                System.out.println("Thread " + threadId + " using resource: " + sharedResource);
            }
        }
    }

    static class LockStarvation {
        private final Object lock = new Object();
        private int accessCount = 0;

        public void doWork(int threadId) {
            synchronized (lock) {
                accessCount++;
                System.out.println("Thread " + threadId + " got lock (access #" + accessCount + ")");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class FairLockDemo {
        private final java.util.concurrent.locks.ReentrantLock fairLock =
            new java.util.concurrent.locks.ReentrantLock(true); // fair

        public void doWork(int threadId) {
            fairLock.lock();
            try {
                System.out.println("Thread " + threadId + " got fair lock");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                fairLock.unlock();
            }
        }
    }

    static class GreedyThreadDemo {
        private final Object lock = new Object();
        private int greedyCount = 0;
        private int politeCount = 0;

        public void greedyWork() {
            synchronized (lock) {
                greedyCount++;
                try {
                    Thread.sleep(50);  // Hold lock for a while
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void politeWork() {
            synchronized (lock) {
                politeCount++;
            }
        }

        public int getGreedyCount() {
            return greedyCount;
        }

        public int getPoliteCount() {
            return politeCount;
        }
    }
}
