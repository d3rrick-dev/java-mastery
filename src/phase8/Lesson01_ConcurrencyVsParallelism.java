package phase8;

/**
 * LESSON 1: CONCURRENCY VS PARALLELISM
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Concurrency: Dealing with multiple tasks at once (not necessarily
 *              at the same time). Like a chef managing multiple dishes.
 *
 * Parallelism: Actually doing multiple tasks at the same time.
 *              Like multiple chefs cooking simultaneously.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Concurrency: Better resource utilization, responsiveness
 * - Parallelism: Faster execution on multi-core systems
 * - Different problems need different approaches
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Coffee shop:
 * - Concurrency: Barista handles multiple orders (switches between them)
 * - Parallelism: Multiple baristas making drinks simultaneously
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Web server:
 * - Concurrency: Single thread handles multiple connections (async I/O)
 * - Parallelism: Multiple threads handle connections simultaneously
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What's the difference between concurrency and parallelism?"
 * Answer: Concurrency is about structure, parallelism is about execution
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using them interchangeably
 * - Assuming concurrency = faster
 * - Using parallelism for I/O-bound tasks
 * - Not understanding when each is appropriate
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Concurrency: Better for I/O-bound, single-core
 * - Parallelism: Better for CPU-bound, multi-core
 * - Concurrency overhead: context switching
 * - Parallelism overhead: thread creation, coordination
 *
 * ============================================================
 * CONCURRENCY vs PARALLELISM
 * ============================================================
 *
 *   CONCURRENCY              PARALLELISM
 *   +----------------+       +----------------+
 *   | Structure      |       | Execution      |
 *   | Decomposition  |       | Simultaneous   |
 *   | Single core OK |       | Multi-core     |
 *   | I/O-bound      |       | CPU-bound      |
 *   | Async/Event    |       | Multi-thread   |
 *   +----------------+       +----------------+
 */

public class Lesson01_ConcurrencyVsParallelism {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CONCURRENCY VS PARALLELISM ===\n");

        // ============================================================
        // EXAMPLE 1: Concurrency (single thread, time-slicing)
        // ============================================================
        System.out.println("--- Example 1: Concurrency (Single Thread) ---\n");

        long start = System.nanoTime();

        // Simulate concurrent tasks on single thread
        taskA();
        taskB();
        taskC();

        long time = System.nanoTime() - start;
        System.out.println("Concurrent (single thread) time: " + time / 1_000_000 + "ms\n");

        // ============================================================
        // EXAMPLE 2: Parallelism (multiple threads)
        // ============================================================
        System.out.println("--- Example 2: Parallelism (Multiple Threads) ---\n");

        start = System.nanoTime();

        Thread t1 = new Thread(() -> taskA());
        Thread t2 = new Thread(() -> taskB());
        Thread t3 = new Thread(() -> taskC());

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        time = System.nanoTime() - start;
        System.out.println("Parallel (multi-thread) time: " + time / 1_000_000 + "ms\n");

        // ============================================================
        // EXAMPLE 3: When to use which
        // ============================================================
        System.out.println("--- Example 3: When to Use Which ---\n");

        System.out.println("Use CONCURRENCY when:");
        System.out.println("  - Tasks are I/O-bound (network, disk)");
        System.out.println("  - Single core is sufficient");
        System.out.println("  - Low memory footprint needed");
        System.out.println("  - Async/event-driven architecture");
        System.out.println();
        System.out.println("Use PARALLELISM when:");
        System.out.println("  - Tasks are CPU-bound");
        System.out.println("  - Multiple cores available");
        System.out.println("  - Tasks are independent");
        System.out.println("  - Throughput is critical");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: CPU-bound vs I/O-bound
        // ============================================================
        System.out.println("--- Example 4: CPU-bound vs I/O-bound ---\n");

        // CPU-bound task
        start = System.nanoTime();
        cpuBoundTask();
        time = System.nanoTime() - start;
        System.out.println("CPU-bound single thread: " + time / 1_000_000 + "ms");

        start = System.nanoTime();
        Thread cpuThread1 = new Thread(() -> cpuBoundTask());
        Thread cpuThread2 = new Thread(() -> cpuBoundTask());
        cpuThread1.start();
        cpuThread2.start();
        cpuThread1.join();
        cpuThread2.join();
        time = System.nanoTime() - start;
        System.out.println("CPU-bound parallel (2 threads): " + time / 1_000_000 + "ms");
        System.out.println("(Speedup limited by cores)\n");

        // I/O-bound task
        start = System.nanoTime();
        ioBoundTask();
        time = System.nanoTime() - start;
        System.out.println("I/O-bound single thread: " + time / 1_000_000 + "ms");

        start = System.nanoTime();
        Thread ioThread1 = new Thread(() -> ioBoundTask());
        Thread ioThread2 = new Thread(() -> ioBoundTask());
        ioThread1.start();
        ioThread2.start();
        ioThread1.join();
        ioThread2.join();
        time = System.nanoTime() - start;
        System.out.println("I/O-bound parallel (2 threads): " + time / 1_000_000 + "ms");
        System.out.println("(Big speedup because I/O overlaps)\n");

        // ============================================================
        // EXAMPLE 5: Amdahl's Law
        // ============================================================
        System.out.println("--- Example 5: Amdahl's Law ---\n");

        System.out.println("Amdahl's Law: Speedup = 1 / ((1 - P) + P/N)");
        System.out.println("  P = parallel portion");
        System.out.println("  N = number of processors");
        System.out.println();
        System.out.println("If 50% is parallel (P=0.5):");
        System.out.println("  2 cores: speedup = " + amdahl(0.5, 2));
        System.out.println("  4 cores: speedup = " + amdahl(0.5, 4));
        System.out.println("  8 cores: speedup = " + amdahl(0.5, 8));
        System.out.println();
        System.out.println("If 90% is parallel (P=0.9):");
        System.out.println("  2 cores: speedup = " + amdahl(0.9, 2));
        System.out.println("  4 cores: speedup = " + amdahl(0.9, 4));
        System.out.println("  8 cores: speedup = " + amdahl(0.9, 8));
        System.out.println();
        System.out.println("Key insight: More parallelism = diminishing returns");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    static void taskA() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task A done");
    }

    static void taskB() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task B done");
    }

    static void taskC() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task C done");
    }

    static void cpuBoundTask() {
        long sum = 0;
        for (int i = 0; i < 10_000_000; i++) {
            sum += i;
        }
    }

    static void ioBoundTask() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static double amdahl(double p, int n) {
        return 1.0 / ((1 - p) + p / n);
    }
}
