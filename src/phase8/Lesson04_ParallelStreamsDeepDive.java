package phase8;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.*;

/**
 * LESSON 4: PARALLEL STREAMS DEEP DIVE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Parallel streams use ForkJoinPool to process elements in parallel.
 * The stream is split into chunks, each processed by a different thread.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Automatic parallelization
 * - Leverage multi-core CPUs
 * - No manual thread management
 * - Functional programming style
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Processing 1M log entries:
 * - parallelStream() splits into chunks
 * - Each core processes a chunk
 * - Results combined automatically
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * ETL pipeline:
 * - Extract: parallel read from database
 * - Transform: parallel data processing
 * - Load: parallel write to data warehouse
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "When should you use parallelStream()?"
 * Answer: Large datasets, CPU-bound, stateless operations
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using for small datasets
 * - Using with stateful operations
 * - Modifying shared state
 * - Assuming order is free
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Splitting cost: O(log n)
 * - Merging cost: O(n)
 * - Best for: n > 10,000, CPU-bound
 * - Worst for: n < 1,000, I/O-bound
 */

public class Lesson04_ParallelStreamsDeepDive {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== PARALLEL STREAMS DEEP DIVE ===\n");

        // ============================================================
        // EXAMPLE 1: When parallel helps
        // ============================================================
        System.out.println("--- Example 1: When Parallel Helps ---\n");

        List<Integer> largeList = IntStream.rangeClosed(1, 1_000_000)
            .boxed()
            .toList();

        // Sequential
        long start = System.nanoTime();
        long seqSum = largeList.stream()
            .filter(Lesson04_ParallelStreamsDeepDive::isPrime)
            .count();
        long seqTime = System.nanoTime() - start;

        // Parallel
        start = System.nanoTime();
        long parSum = largeList.parallelStream()
            .filter(Lesson04_ParallelStreamsDeepDive::isPrime)
            .count();
        long parTime = System.nanoTime() - start;

        System.out.println("Sequential: " + seqSum + " primes in " + seqTime / 1_000_000 + "ms");
        System.out.println("Parallel: " + parSum + " primes in " + parTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) seqTime / parTime) + "x\n");

        // ============================================================
        // EXAMPLE 2: When parallel hurts
        // ============================================================
        System.out.println("--- Example 2: When Parallel Hurts ---\n");

        List<Integer> smallList = IntStream.rangeClosed(1, 100)
            .boxed()
            .toList();

        start = System.nanoTime();
        smallList.stream()
            .map(n -> n * 2)
            .count();
        seqTime = System.nanoTime() - start;

        start = System.nanoTime();
        smallList.parallelStream()
            .map(n -> n * 2)
            .count();
        parTime = System.nanoTime() - start;

        System.out.println("Sequential (small): " + seqTime / 1_000_000 + "ms");
        System.out.println("Parallel (small): " + parTime / 1_000_000 + "ms");
        System.out.println("Parallel is SLOWER for small datasets!\n");

        // ============================================================
        // EXAMPLE 3: Order preservation cost
        // ============================================================
        System.out.println("--- Example 3: Order Preservation ---\n");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("forEachOrdered (preserves order):");
        numbers.parallelStream()
            .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("forEach (no order guarantee):");
        numbers.parallelStream()
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        // ============================================================
        // EXAMPLE 4: Stateful operations in parallel
        // ============================================================
        System.out.println("--- Example 4: Stateful Operations ---\n");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("sorted() in parallel (expensive):");
        start = System.nanoTime();
        nums.parallelStream()
            .sorted()
            .forEach(n -> System.out.print(n + " "));
        parTime = System.nanoTime() - start;
        System.out.println(" Time: " + parTime / 1_000_000 + "ms\n");

        System.out.println("distinct() in parallel (expensive):");
        List<Integer> withDups = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        start = System.nanoTime();
        withDups.parallelStream()
            .distinct()
            .forEach(n -> System.out.print(n + " "));
        parTime = System.nanoTime() - start;
        System.out.println(" Time: " + parTime / 1_000_000 + "ms\n");

        // ============================================================
        // EXAMPLE 5: Thread-safe collectors
        // ============================================================
        System.out.println("--- Example 5: Thread-Safe Collectors ---\n");

        List<Integer> data = IntStream.rangeClosed(1, 1000)
            .boxed()
            .collect(Collectors.toList());

        // toSet() is thread-safe
        Set<Integer> parallelSet = data.parallelStream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toSet());
        System.out.println("Parallel set size: " + parallelSet.size());

        // toMap() needs merge function for parallel
        Map<Integer, Integer> parallelMap = data.parallelStream()
            .collect(Collectors.toMap(
                n -> n,
                n -> n * 2,
                (a, b) -> a  // Merge function for duplicates
            ));
        System.out.println("Parallel map size: " + parallelMap.size() + "\n");

        // ============================================================
        // EXAMPLE 6: Custom Spliterator
        // ============================================================
        System.out.println("--- Example 6: Spliterator ---\n");

        System.out.println("Spliterator splits data for parallel processing");
        System.out.println("Characteristics: SIZED, SUBSIZED, ORDERED, etc.");
        System.out.println("Custom Spliterator controls parallel efficiency\n");

        // ============================================================
        // EXAMPLE 7: Common ForkJoinPool
        // ============================================================
        System.out.println("--- Example 7: Common ForkJoinPool ---\n");

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("Common pool parallelism: " + commonPool.getParallelism());
        System.out.println("(Usually equals available processors)");
        System.out.println();

        // ============================================================
        // EXAMPLE 8: Custom ForkJoinPool for parallelStream
        // ============================================================
        System.out.println("--- Example 8: Custom Pool ---\n");

        ForkJoinPool customPool = new ForkJoinPool(4);

        customPool.submit(() -> {
            long customSum = IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .asLongStream()
                .sum();
            System.out.println("Custom pool sum: " + customSum);
        }).get();

        customPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 9: Guidelines
        // ============================================================
        System.out.println("--- Example 9: Guidelines ---\n");

        System.out.println("Use parallelStream when:");
        System.out.println("  - Dataset is large (> 10,000 elements)");
        System.out.println("  - Operations are CPU-bound");
        System.out.println("  - Operations are stateless");
        System.out.println("  - Operations are independent");
        System.out.println();
        System.out.println("Avoid parallelStream when:");
        System.out.println("  - Dataset is small");
        System.out.println("  - Operations are I/O-bound");
        System.out.println("  - Operations have side effects");
        System.out.println("  - Order matters (forEachOrdered is slow)");
    }

    // ============================================================
    // HELPER METHOD
    // ============================================================

    static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
}
