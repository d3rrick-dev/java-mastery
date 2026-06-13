package phase7;

/**
 * LESSON 1: PROCESSES VS THREADS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A PROCESS is an independent execution unit with its own memory space.
 * Think of it as a separate house with its own kitchen, bedroom, and bathroom.
 *
 * A THREAD is the smallest unit of execution within a process.
 * Think of it as a worker inside that house, sharing the same kitchen and bedroom
 * with other workers (threads) in the same house.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Processes provide isolation (crash in one doesn't affect others)
 * - Threads allow concurrent work within the same application
 * - Threads are lighter weight (less memory, faster to create)
 * - Threads can share data directly (no IPC needed)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Web Browser:
 * - Process: The entire browser application
 * - Threads: One for UI rendering, one for network requests, one for JavaScript execution
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Web Server (like Tomcat):
 * - Each incoming HTTP request can be handled by a separate thread
 * - All threads share the same application memory (connection pools, caches)
 * - If one request handler crashes, other threads keep running
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * Common question: "What's the difference between a process and a thread?"
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Thinking threads are completely independent (they share memory!)
 * - Not realizing thread crashes can bring down the whole process
 * - Overusing threads (too many threads = context switching overhead)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Process creation: ~1ms (heavy)
 * - Thread creation: ~10-100 microseconds (light)
 * - Context switch between threads: ~1-10 microseconds
 * - Context switch between processes: ~10-100 microseconds
 *
 * ============================================================
 * DIAGRAM: PROCESS vs THREAD
 * ============================================================
 *
 *   PROCESS A                          PROCESS B
 *   +------------------+               +------------------+
 *   |  Memory Space A  |               |  Memory Space B  |
 *   |  +------------+  |               |  +------------+  |
 *   |  | Thread A1  |  |               |  | Thread B1  |  |
 *   |  |------------|  |               |  |------------|  |
 *   |  | Thread A2  |  |               |  | Thread B2  |  |
 *   |  |------------|  |               |  |------------|  |
 *   |  | Thread A3  |  |               |  | Thread B3  |  |
 *   |  +------------+  |               |  +------------+  |
 *   +------------------+               +------------------+
 *
 *   Threads A1, A2, A3 share Memory Space A
 *   Threads B1, B2, B3 share Memory Space B
 *   Process A and Process B are completely isolated
 *
 * ============================================================
 * THREAD EXECUTION FLOW
 * ============================================================
 *
 *   Main Thread
 *      |
 *      +---> Creates Worker Thread 1
 *      |         |
 *      |         +---> Runs concurrently
 *      |
 *      +---> Creates Worker Thread 2
 *      |         |
 *      |         +---> Runs concurrently
 *      |
 *      +---> Continues main work
 *      |
 *      +---> Waits for workers (join)
 *      |
 *      +---> Completes
 */

public class Lesson01_ProcessesVsThreads {

    public static void main(String[] args) {
        System.out.println("=== PROCESSES VS THREADS ===\n");

        // ============================================================
        // EXAMPLE 1: Multiple threads in same process
        // ============================================================
        System.out.println("--- Example 1: Multiple Threads Sharing Memory ---");

        SharedCounter counter = new SharedCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final counter value: " + counter.getCount());
        System.out.println("Expected: 2000 (but race condition may give less!)\n");

        // ============================================================
        // EXAMPLE 2: Demonstrating thread identity
        // ============================================================
        System.out.println("--- Example 2: Thread Identity ---");

        Thread currentThread = Thread.currentThread();
        System.out.println("Current thread: " + currentThread.getName());
        System.out.println("Thread ID: " + currentThread.threadId());
        System.out.println("Thread priority: " + currentThread.getPriority());
        System.out.println("Thread state: " + currentThread.getState());
        System.out.println("Is daemon: " + currentThread.isDaemon());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Process isolation demonstration
        // ============================================================
        System.out.println("--- Example 3: Process Isolation ---");
        System.out.println("Each Java application runs in its own JVM process.");
        System.out.println("If you run this class twice, they are separate processes.");
        System.out.println("Process ID: " + ProcessHandle.current().pid());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Thread vs Process performance
        // ============================================================
        System.out.println("--- Example 4: Performance Comparison ---");

        // Measure thread creation time
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {}).start();
        }
        long threadTime = System.nanoTime() - start;
        System.out.println("Time to create 1000 threads: " + (threadTime / 1_000_000) + "ms");

        // ============================================================
        // EXAMPLE 5: When to use threads vs processes
        // ============================================================
        System.out.println("\n--- Example 5: When to Use What ---");
        System.out.println("Use THREADS when:");
        System.out.println("  - Tasks need to share data frequently");
        System.out.println("  - You need low-latency communication");
        System.out.println("  - Memory is a concern");
        System.out.println();
        System.out.println("Use PROCESSES when:");
        System.out.println("  - Tasks need strong isolation");
        System.out.println("  - One task crashing shouldn't affect others");
        System.out.println("  - You need to use multiple CPUs fully (no GIL in Java)");
    }

    /**
     * Shared counter demonstrating shared memory between threads.
     * This has a RACE CONDITION bug (demonstrated in Lesson 11).
     */
    static class SharedCounter {
        private int count = 0;

        public void increment() {
            count++;  // NOT thread-safe! (read-modify-write is not atomic)
        }

        public int getCount() {
            return count;
        }
    }
}
