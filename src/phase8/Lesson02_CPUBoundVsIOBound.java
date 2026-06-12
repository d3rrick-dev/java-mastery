package phase8;

/**
 * LESSON 2: CPU-BOUND VS I/O-BOUND WORKLOADS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * CPU-bound: Task limited by processor speed (calculations, processing)
 * I/O-bound: Task limited by input/output speed (network, disk, database)
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Different workloads need different thread strategies
 * - CPU-bound: More threads = more context switching (bad)
 * - I/O-bound: More threads = better utilization (good)
 * - Wrong strategy wastes resources
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * CPU-bound: Video encoding, image processing, data analysis
 * I/O-bound: API calls, database queries, file reading
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * CPU-bound: Data aggregation, report generation, encryption
 * I/O-bound: REST API calls, database queries, cache lookups
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How do you determine thread pool size?"
 * Answer: CPU-bound = cores, I/O-bound = cores * (1 + wait/compute)
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using same thread pool for both types
 * - Too many threads for CPU-bound (context switching)
 * - Too few threads for I/O-bound (underutilization)
 * - Not measuring actual wait times
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - CPU-bound: Optimal threads = CPU cores
 * - I/O-bound: Optimal threads = cores * (1 + waitTime/computeTime)
 * - Context switch: ~1-10 microseconds
 * - Too many threads = memory exhaustion
 *
 * ============================================================
 * WORKLOAD CHARACTERISTICS
 * ============================================================
 *
 *   CPU-BOUND                I/O-BOUND
 *   +----------------+       +----------------+
 *   | Heavy calc     |       | Waiting        |
 *   | Low wait       |       | High wait      |
 *   | Use fewer      |       | Use more       |
 *   | threads        |       | threads        |
 *   +----------------+       +----------------+
 *   | cores = threads|       | cores * N      |
 *   +----------------+       +----------------+
 */

public class Lesson02_CPUBoundVsIOBound {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CPU-BOUND VS I/O-BOUND ===\n");

        // ============================================================
        // EXAMPLE 1: CPU-bound task
        // ============================================================
        System.out.println("--- Example 1: CPU-Bound Task ---\n");

        long start = System.nanoTime();
        cpuBoundTask(10_000_000);
        long singleTime = System.nanoTime() - start;
        System.out.println("Single thread: " + singleTime / 1_000_000 + "ms");

        start = System.nanoTime();
        Thread t1 = new Thread(() -> cpuBoundTask(5_000_000));
        Thread t2 = new Thread(() -> cpuBoundTask(5_000_000));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long parallelTime = System.nanoTime() - start;
        System.out.println("2 threads: " + parallelTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) singleTime / parallelTime) + "x\n");

        // ============================================================
        // EXAMPLE 2: I/O-bound task
        // ============================================================
        System.out.println("--- Example 2: I/O-Bound Task ---\n");

        start = System.nanoTime();
        ioBoundTask(5);
        singleTime = System.nanoTime() - start;
        System.out.println("Single thread: " + singleTime / 1_000_000 + "ms");

        start = System.nanoTime();
        Thread io1 = new Thread(() -> ioBoundTask(5));
        Thread io2 = new Thread(() -> ioBoundTask(5));
        io1.start();
        io2.start();
        io1.join();
        io2.join();
        parallelTime = System.nanoTime() - start;
        System.out.println("2 threads: " + parallelTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) singleTime / parallelTime) + "x\n");

        // ============================================================
        // EXAMPLE 3: Optimal thread count calculation
        // ============================================================
        System.out.println("--- Example 3: Optimal Thread Count ---\n");

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available cores: " + cores);
        System.out.println();

        // CPU-bound: threads = cores
        System.out.println("CPU-bound optimal threads: " + cores);
        System.out.println("Formula: N_threads = N_cores");
        System.out.println();

        // I/O-bound: threads = cores * (1 + wait/compute)
        double waitTime = 100;  // ms waiting
        double computeTime = 10;  // ms computing
        int ioThreads = (int) Math.ceil(cores * (1 + waitTime / computeTime));
        System.out.println("I/O-bound optimal threads: " + ioThreads);
        System.out.println("Formula: N_threads = N_cores * (1 + waitTime/computeTime)");
        System.out.println("  waitTime=" + waitTime + "ms, computeTime=" + computeTime + "ms");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Mixed workload
        // ============================================================
        System.out.println("--- Example 4: Mixed Workload ---\n");

        System.out.println("For mixed workloads:");
        System.out.println("1. Profile to find actual ratios");
        System.out.println("2. Use formula: cores * (1 + wait/compute)");
        System.out.println("3. Test different pool sizes");
        System.out.println("4. Monitor thread utilization");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Thread pool sizing
        // ============================================================
        System.out.println("--- Example 5: Thread Pool Sizing ---\n");

        System.out.println("FixedThreadPool sizing:");
        System.out.println("  CPU-bound: Executors.newFixedThreadPool(cores)");
        System.out.println("  I/O-bound: Executors.newFixedThreadPool(cores * 2)");
        System.out.println("  High I/O: Executors.newFixedThreadPool(cores * 4)");
        System.out.println();

        System.out.println("CachedThreadPool:");
        System.out.println("  Good for: Many short I/O tasks");
        System.out.println("  Bad for: CPU-bound (creates too many threads)");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Measuring task type
        // ============================================================
        System.out.println("--- Example 6: Measuring Task Type ---\n");

        // Measure CPU time
        long cpuStart = System.nanoTime();
        cpuBoundTask(1_000_000);
        long cpuTime = System.nanoTime() - cpuStart;
        System.out.println("CPU time for 1M ops: " + cpuTime / 1_000_000 + "ms");

        // Measure I/O time
        long ioStart = System.nanoTime();
        ioBoundTask(1);
        long ioTime = System.nanoTime() - ioStart;
        System.out.println("I/O time for 1 op: " + ioTime / 1_000_000 + "ms");

        double ratio = (double) ioTime / cpuTime;
        System.out.println("I/O to CPU ratio: " + String.format("%.2f", ratio));
        System.out.println("(High ratio = more threads needed)");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    static void cpuBoundTask(int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += Math.sqrt(i) * Math.sin(i);
        }
    }

    static void ioBoundTask(int operations) {
        for (int i = 0; i < operations; i++) {
            try {
                Thread.sleep(100);  // Simulate I/O wait
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
