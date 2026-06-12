package phase7;

import java.util.*;
import java.util.stream.*;

/**
 * LESSON 11: PARALLEL STREAMS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Parallel streams use multiple threads to process stream elements.
 * Instead of processing one element at a time, they process
 * multiple elements simultaneously using ForkJoinPool.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Leverage multi-core CPUs
 * - Automatic parallelization (just add .parallel())
 * - Uses ForkJoinPool.commonPool() under the hood
 * - No manual thread management needed
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Image processing:
 * - Process 1000 images
 * - parallelStream() uses all CPU cores
 * - Each core processes different images
 * - Results combined automatically
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Log analysis:
 * - Process millions of log entries
 * - parallelStream() distributes across cores
 * - Filter, map, reduce all parallelized
 * - Much faster than sequential stream
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is parallelStream()?"
 * Answer: Stream that uses multiple threads for processing,
 *         uses ForkJoinPool.commonPool()
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using parallelStream() for small datasets (overhead > benefit)
 * - Using with non-thread-safe operations
 * - Assuming order is preserved (it is, but slower)
 * - Blocking operations in parallel stream
 * - Modifying shared state
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Parallelism overhead: splitting, merging, coordination
 * - Best for: CPU-bound, large datasets (10,000+ elements)
 * - Worst for: I/O-bound, small datasets, ordered operations
 * - Spliterator cost affects performance
 *
 * ============================================================
 * SEQUENTIAL vs PARALLEL STREAM
 * ============================================================
 *
 *   Sequential Stream          Parallel Stream
 *   +----------------+         +----------------+
 *   | Single thread  |         | Multiple threads|
 *   | One element    |         | Multiple        |
 *   | at a time      |         | elements        |
 *   +----------------+         +----------------+
 *   | Order: natural |         | Order: preserved|
 *   | Fast for small |         | Fast for large  |
 *   | No overhead    |         | Splitting cost  |
 *   +----------------+         +----------------+
 */

public class Lesson11_ParallelStreams {

    public static void main(String[] args) {
        System.out.println("=== PARALLEL STREAMS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic parallel stream
        // ============================================================
        System.out.println("--- Example 1: Basic Parallel Stream ---\n");

        List<Integer> numbers = IntStream.rangeClosed(1, 100)
            .boxed()
            .collect(Collectors.toList());

        // Sequential
        long startSeq = System.nanoTime();
        long sumSeq = numbers.stream()
            .mapToLong(Integer::longValue)
            .sum();
        long timeSeq = System.nanoTime() - startSeq;

        // Parallel
        long startPar = System.nanoTime();
        long sumPar = numbers.parallelStream()
            .mapToLong(Integer::longValue)
            .sum();
        long timePar = System.nanoTime() - startPar;

        System.out.println("Sequential sum: " + sumSeq + " in " + timePar / 1_000_000 + "ms");
        System.out.println("Parallel sum: " + sumPar + " in " + timePar / 1_000_000 + "ms");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: When parallel is faster
        // ============================================================
        System.out.println("--- Example 2: Performance Comparison ---\n");

        List<Integer> largeList = IntStream.rangeClosed(1, 1_000_000)
            .boxed()
            .collect(Collectors.toList());

        // Sequential
        startSeq = System.nanoTime();
        long countSeq = largeList.stream()
            .filter(n -> n % 2 == 0)
            .count();
        timeSeq = System.nanoTime() - startSeq;

        // Parallel
        startPar = System.nanoTime();
        long countPar = largeList.parallelStream()
            .filter(n -> n % 2 == 0)
            .count();
        timePar = System.nanoTime() - startPar;

        System.out.println("Sequential: " + countSeq + " in " + timeSeq / 1_000_000 + "ms");
        System.out.println("Parallel: " + countPar + " in " + timePar / 1_000_000 + "ms");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Parallel stream with custom operations
        // ============================================================
        System.out.println("--- Example 3: Custom Operations ---\n");

        List<String> words = Arrays.asList(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "kiwi", "lemon"
        );

        // Parallel map
        List<Integer> lengths = words.parallelStream()
            .map(String::length)
            .collect(Collectors.toList());

        System.out.println("Word lengths: " + lengths);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Parallel reduce
        // ============================================================
        System.out.println("--- Example 4: Parallel Reduce ---\n");

        List<Integer> nums = IntStream.rangeClosed(1, 1000)
            .boxed()
            .collect(Collectors.toList());

        // Parallel reduce (must be associative!)
        int parallelSum = nums.parallelStream()
            .reduce(0, Integer::sum);

        System.out.println("Parallel reduce sum: " + parallelSum);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Parallel forEach (unordered)
        // ============================================================
        System.out.println("--- Example 5: Parallel forEach ---\n");

        System.out.println("forEachOrdered (preserves order):");
        numbers.parallelStream()
            .limit(10)
            .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("forEach (no order guarantee):");
        numbers.parallelStream()
            .limit(10)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        // ============================================================
        // EXAMPLE 6: Thread safety issues
        // ============================================================
        System.out.println("--- Example 6: Thread Safety ---\n");

        // WRONG: Modifying shared state
        List<Integer> unsafeList = new ArrayList<>();
        try {
            numbers.parallelStream()
                .limit(100)
                .forEach(unsafeList::add);  // ArrayList is not thread-safe!
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // CORRECT: Use thread-safe collection
        List<Integer> safeList = Collections.synchronizedList(new ArrayList<>());
        numbers.parallelStream()
            .limit(100)
            .forEach(safeList::add);

        System.out.println("Safe list size: " + safeList.size() + "\n");

        // ============================================================
        // EXAMPLE 7: When NOT to use parallelStream
        // ============================================================
        System.out.println("--- Example 7: When NOT to Use Parallel ---\n");

        System.out.println("Don't use parallelStream when:");
        System.out.println("1. Dataset is small (< 10,000 elements)");
        System.out.println("2. Operations are I/O-bound (network, disk)");
        System.out.println("3. Operations are not thread-safe");
        System.out.println("4. Order matters (forEachOrdered is slower)");
        System.out.println("5. Operations are simple (overhead > benefit)");
        System.out.println("6. Already using async/CompletableFuture");
        System.out.println();

        // ============================================================
        // EXAMPLE 8: Custom ForkJoinPool for parallelStream
        // ============================================================
        System.out.println("--- Example 8: Custom ForkJoinPool ---\n");

        ForkJoinPool customPool = new ForkJoinPool(4);

        customPool.submit(() -> {
            long customSum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
            System.out.println("Custom pool sum: " + customSum);
        }).get();

        customPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 9: Parallel stream with grouping
        // ============================================================
        System.out.println("--- Example 9: Parallel Grouping ---\n");

        List<Integer> range = IntStream.rangeClosed(1, 100)
            .boxed()
            .collect(Collectors.toList());

        Map<Integer, List<Integer>> byMod = range.parallelStream()
            .collect(Collectors.groupingBy(n -> n % 10));

        System.out.println("Grouped by mod 10: " + byMod.keySet());
        System.out.println();
    }
}
