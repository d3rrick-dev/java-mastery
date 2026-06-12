package phase11;

import java.util.*;
import java.util.stream.*;

/**
 * LESSON 19: PARALLEL STREAM VS STREAM
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Stream: Sequential processing (single thread)
 * Parallel Stream: Parallel processing (multiple threads)
 * Like one worker vs a team of workers.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Stream: Simple, predictable, ordered
 * - Parallel Stream: Faster for large datasets
 * - Automatic parallelization
 */

public class Lesson19_ParallelStreamVsStream {

    public static void main(String[] args) {
        System.out.println("=== PARALLEL STREAM VS STREAM ===\n");

        // ============================================================
        // EXAMPLE 1: Basic comparison
        // ============================================================
        System.out.println("--- Example 1: Basic Comparison ---\n");

        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
            .boxed()
            .toList();

        // Sequential
        long start = System.nanoTime();
        long sumSeq = numbers.stream()
            .mapToLong(n -> compute(n))
            .sum();
        long seqTime = System.nanoTime() - start;

        // Parallel
        start = System.nanoTime();
        long sumPar = numbers.parallelStream()
            .mapToLong(n -> compute(n))
            .sum();
        long parTime = System.nanoTime() - start;

        System.out.println("Sequential: " + seqTime / 1_000_000 + " ms");
        System.out.println("Parallel: " + parTime / 1_000_000 + " ms");
        System.out.println("Results equal: " + (sumSeq == sumPar));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: When parallel is faster
        // ============================================================
        System.out.println("--- Example 2: When Parallel Helps ---\n");

        System.out.println("Parallel stream is faster when:");
        System.out.println("  - Large dataset (> 10,000 elements)");
        System.out.println("  - CPU-intensive operations");
        System.out.println("  - Multiple CPU cores available");
        System.out.println("  - Low contention");
        System.out.println();
        System.out.println("Parallel stream is SLOWER when:");
        System.out.println("  - Small dataset");
        System.out.println("  - I/O-bound operations");
        System.out.println("  - Ordered operations (forEachOrdered)");
        System.out.println("  - High overhead (boxing/unboxing)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Order preservation
        // ============================================================
        System.out.println("--- Example 3: Order Preservation ---\n");

        List<Integer> small = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("Sequential forEach:");
        small.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("Parallel forEach (order NOT guaranteed):");
        small.parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("Parallel forEachOrdered (order preserved):");
        small.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        System.out.println("\n");

        // ============================================================
        // EXAMPLE 4: Thread pool
        // ============================================================
        System.out.println("--- Example 4: Thread Pool ---\n");

        System.out.println("Parallel streams use ForkJoinPool.commonPool():");
        System.out.println("  - Default parallelism: CPU cores - 1");
        System.out.println("  - Can set: System.setProperty(...)");
        System.out.println("  - Or use custom ForkJoinPool");
        System.out.println();
        System.out.println("Custom pool:");
        System.out.println("  ForkJoinPool customPool = new ForkJoinPool(4);");
        System.out.println("  customPool.submit(() -> list.parallelStream()...");
        System.out.println();
    }

    // ============================================================
    // HELPER METHOD
    // ============================================================
    static long compute(int n) {
        long sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum + n;
    }

    // ============================================================
    // PARALLEL VS SEQUENTIAL DETAILS
    // ============================================================
    /*
     * Stream vs ParallelStream:
     *
     * 1. Execution:
     *    - Stream: Single thread, sequential
     *    - ParallelStream: Multiple threads, ForkJoinPool
     *
     * 2. Order:
     *    - Stream: Preserves encounter order
     *    - ParallelStream: forEach() may reorder
     *    - forEachOrdered() preserves order
     *
     * 3. Performance:
     *    - Stream: Better for small data, I/O-bound
     *    - ParallelStream: Better for large data, CPU-bound
     *
     * 4. Side Effects:
     *    - Stream: Safer with side effects
     *    - ParallelStream: Side effects cause bugs
     *
     * 5. Thread Safety:
     *    - Stream: No thread safety needed
     *    - ParallelStream: Must be thread-safe
     *
     * Best Practices:
     * - Use parallelStream() only for CPU-intensive work
     * - Avoid side effects in parallel streams
     * - Use forEachOrdered() if order matters
     * - Benchmark before using
     */
}
