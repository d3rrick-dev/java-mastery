package phase11;

import java.util.*;

/**
 * LESSON 13: STREAMS INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Streams are pipelines that process data lazily.
 * Like an assembly line - each station does one operation.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Declarative data processing
 * - Lazy evaluation (only compute when needed)
 * - Parallel processing support
 * - Functional style
 */

public class Lesson13_StreamsInternals {

    public static void main(String[] args) {
        System.out.println("=== STREAMS INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Stream pipeline
        // ============================================================
        System.out.println("--- Example 1: Stream Pipeline ---\n");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Stream pipeline: source -> intermediate -> terminal
        long count = numbers.stream()
            .filter(n -> n % 2 == 0)      // Intermediate
            .map(n -> n * n)               // Intermediate
            .count();                       // Terminal

        System.out.println("Even squares count: " + count);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Lazy evaluation
        // ============================================================
        System.out.println("--- Example 2: Lazy Evaluation ---\n");

        System.out.println("Intermediate ops are LAZY:");
        System.out.println("  - filter(), map(), sorted() don't execute immediately");
        System.out.println("  - Only execute when terminal op is called");
        System.out.println("  - Elements processed one at a time");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Stream types
        // ============================================================
        System.out.println("--- Example 3: Stream Types ---\n");

        System.out.println("1. Sequential Stream:");
        System.out.println("   - Single thread");
        System.out.println("   - numbers.stream()");
        System.out.println();
        System.out.println("2. Parallel Stream:");
        System.out.println("   - Multiple threads (ForkJoinPool)");
        System.out.println("   - numbers.parallelStream()");
        System.out.println();
        System.out.println("3. Infinite Stream:");
        System.out.println("   - Stream.generate(), Stream.iterate()");
        System.out.println("   - Must use limit() to terminate");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Short-circuiting
        // ============================================================
        System.out.println("--- Example 4: Short-Circuiting ---\n");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        // findFirst - stops after first match
        Optional<Integer> first = nums.stream()
            .filter(n -> n > 2)
            .findFirst();

        System.out.println("First > 2: " + first.orElse(-1));

        // anyMatch - stops after first match
        boolean hasEven = nums.stream()
            .anyMatch(n -> n % 2 == 0);

        System.out.println("Has even: " + hasEven);
        System.out.println();
    }

    // ============================================================
    // STREAMS INTERNALS
    // ============================================================
    /*
     * Stream Pipeline:
     *
     * 1. Source: Collection, array, generator
     * 2. Intermediate Operations (lazy):
     *    - filter(Predicate)
     *    - map(Function)
     *    - flatMap(Function)
     *    - sorted(Comparator)
     *    - distinct()
     *    - limit(n), skip(n)
     * 3. Terminal Operation (triggers execution):
     *    - forEach(Consumer)
     *    - collect(Collector)
     *    - reduce()
     *    - count()
     *    - findFirst(), findAny()
     *    - anyMatch(), allMatch(), noneMatch()
     *
     * Execution Model:
     * - Pull-based: terminal pulls from source
     * - One element at a time through pipeline
     * - Short-circuiting possible
     *
     * Parallel Streams:
     * - Uses ForkJoinPool.commonPool()
     * - Splits source, processes in parallel
     * - Not always faster (overhead)
     */
}
