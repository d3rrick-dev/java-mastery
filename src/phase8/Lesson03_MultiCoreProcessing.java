package phase8;

import java.util.concurrent.*;

/**
 * LESSON 3: MULTI-CORE PROCESSING
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Multi-core processing means using multiple CPU cores simultaneously
 * to execute tasks. Each core can run its own thread, allowing true
 * parallel execution.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Single-core CPUs hit frequency limits
 * - More cores = more parallel work
 * - Better throughput for parallelizable tasks
 * - Modern CPUs have 4-64+ cores
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Video editing:
 * - Core 1: Video encoding
 * - Core 2: Audio processing
 * - Core 3: Effects rendering
 * - Core 4: Preview generation
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Data processing pipeline:
 * - Core 1: Read from database
 * - Core 2: Transform data
 * - Core 3: Write to cache
 * - Core 4: Generate report
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How many cores does your application use?"
 * Answer: Depends on thread pool size and task type
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Creating more threads than cores (for CPU-bound)
 * - Not considering hyper-threading
 * - Assuming more cores = always faster
 * - Ignoring CPU affinity
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - CPU-bound: Optimal threads = cores
 * - Hyper-threading: 2 threads per core (not 2x speed)
 * - Context switching between cores is expensive
 * - Cache locality matters (same core = faster)
 *
 * ============================================================
 * MULTI-CORE ARCHITECTURE
 * ============================================================
 *
 *   CPU (4 cores)
 *   +---+---+---+---+
 *   | C1| C2| C3| C4|
 *   +---+---+---+---+
 *      |   |   |   |
 *      v   v   v   v
 *   Thread Thread Thread Thread
 *     T1     T2     T3     T4
 *
 *   Each core runs one thread simultaneously
 */

public class Lesson03_MultiCoreProcessing {

    public static void main(String[] args) throws Exception {
        System.out.println("=== MULTI-CORE PROCESSING ===\n");

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processor cores: " + cores);
        System.out.println();

        // ============================================================
        // EXAMPLE 1: Using all cores
        // ============================================================
        System.out.println("--- Example 1: Using All Cores ---\n");

        ExecutorService executor = Executors.newFixedThreadPool(cores);

        long start = System.nanoTime();

        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < cores; i++) {
            futures.add(executor.submit(() -> {
                long sum = 0;
                for (int j = 0; j < 10_000_000; j++) {
                    sum += j;
                }
                return sum;
            }));
        }

        for (Future<Long> f : futures) {
            f.get();
        }

        long time = System.nanoTime() - start;
        System.out.println("Time with " + cores + " threads: " + time / 1_000_000 + "ms");

        executor.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Too many threads (diminishing returns)
        // ============================================================
        System.out.println("--- Example 2: Too Many Threads ---\n");

        int[] threadCounts = {1, 2, 4, 8, 16, 32};

        for (int threadCount : threadCounts) {
            executor = Executors.newFixedThreadPool(threadCount);

            start = System.nanoTime();

            List<Future<Long>> futures2 = new ArrayList<>();
            for (int i = 0; i < threadCount; i++) {
                futures2.add(executor.submit(() -> {
                    long sum = 0;
                    for (int j = 0; j < 10_000_000; j++) {
                        sum += j;
                    }
                    return sum;
                }));
            }

            for (Future<Long> f : futures2) {
                f.get();
            }

            time = System.nanoTime() - start;
            System.out.println(threadCount + " threads: " + time / 1_000_000 + "ms");

            executor.shutdown();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Parallel stream uses all cores
        // ============================================================
        System.out.println("--- Example 3: Parallel Stream ---\n");

        start = System.nanoTime();
        long sum = java.util.stream.IntStream.rangeClosed(1, 10_000_000)
            .parallel()
            .asLongStream()
            .sum();
        time = System.nanoTime() - start;
        System.out.println("Parallel stream sum: " + sum);
        System.out.println("Time: " + time / 1_000_000 + "ms\n");

        // ============================================================
        // EXAMPLE 4: CPU affinity simulation
        // ============================================================
        System.out.println("--- Example 4: CPU Affinity ---\n");

        System.out.println("CPU affinity: Binding threads to specific cores");
        System.out.println("Benefits:");
        System.out.println("  - Better cache locality");
        System.out.println("  - Reduced cache thrashing");
        System.out.println("  - Predictable performance");
        System.out.println();
        System.out.println("Java doesn't expose CPU affinity directly");
        System.out.println("OS/JVM handles thread scheduling");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: NUMA awareness
        // ============================================================
        System.out.println("--- Example 5: NUMA (Non-Uniform Memory Access) ---\n");

        System.out.println("NUMA: Memory access time depends on memory location");
        System.out.println("  - Local memory: Fast");
        System.out.println("  - Remote memory: Slow");
        System.out.println();
        System.out.println("For Java:");
        System.out.println("  - JVM handles NUMA transparently");
        System.out.println("  - Large heap may span multiple NUMA nodes");
        System.out.println("  - Consider -XX:+UseNUMA flag");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Best practices
        // ============================================================
        System.out.println("--- Example 6: Best Practices ---\n");

        System.out.println("1. Match thread count to workload:");
        System.out.println("   CPU-bound: threads = cores");
        System.out.println("   I/O-bound: threads = cores * (1 + wait/compute)");
        System.out.println();
        System.out.println("2. Use ForkJoinPool for recursive tasks:");
        System.out.println("   Work stealing balances load automatically");
        System.out.println();
        System.out.println("3. Consider hyper-threading:");
        System.out.println("   Logical cores = 2x physical (but not 2x speed)");
        System.out.println();
        System.out.println("4. Monitor CPU usage:");
        System.out.println("   If CPU < 80%, add more threads");
        System.out.println("   If CPU > 90%, reduce threads");
        System.out.println();
        System.out.println("5. Profile before optimizing:");
        System.out.println("   Measure, don't guess");
    }
}
