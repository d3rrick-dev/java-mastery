package phase11;

import java.util.*;
import java.util.stream.*;

/**
 * LESSON 19: PARALLEL STREAM VS STREAM
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Basic comparison
 * 2. When parallel is faster
 * 3. Order preservation
 * 4. Thread pool
 * 5. Interview questions
 */

public class Lesson19_ParallelStreamVsStream {
    public static void main(String[] args) {
        System.out.println("""
            === PARALLEL STREAM VS STREAM ===
            
            1. BASIC COMPARISON
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Stream processes sequentially, ParallelStream uses multiple threads.
            
               WHY IT EXISTS:
               - Performance optimization
               - Automatic parallelization
            
               SIMPLE EXAMPLE:
                   // Sequential:
                   long sumSeq = numbers.stream()
                       .mapToLong(n -> compute(n))
                       .sum();
                   
                   // Parallel:
                   long sumPar = numbers.parallelStream()
                       .mapToLong(n -> compute(n))
                       .sum();
                   
                   // Both produce same result
                   // Parallel may be faster for large datasets
            
               REAL-WORLD BACKEND EXAMPLE:
                   A batch processing job:
                   records.parallelStream()
                       .filter(this::isValid)
                       .map(this::transform)
                       .forEach(this::save);
            
               INTERVIEW QUESTION:
                   "When should you use parallelStream()?
                   What are the performance considerations?"
            
               COMMON MISTAKES:
                   - Using for small datasets
                   - Side effects in parallel
            
            ─────────────────────────────────────────────────────────────────────
            
            2. WHEN PARALLEL IS FASTER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ParallelStream excels in specific scenarios.
            
               WHY IT EXISTS:
               - CPU utilization
               - Throughput improvement
            
               SIMPLE EXAMPLE:
                   // Parallel is FASTER when:
                   // - Large dataset (> 10,000 elements)
                   // - CPU-intensive operations
                   // - Multiple CPU cores available
                   // - Low contention
                   
                   // Parallel is SLOWER when:
                   // - Small dataset
                   // - I/O-bound operations
                   // - Ordered operations (forEachOrdered)
                   // - High overhead (boxing/unboxing)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data transformation pipeline:
                   // CPU-intensive:
                   users.parallelStream()
                       .map(this::expensiveCalculation)
                       .toList();
                   
                   // I/O-intensive (avoid parallel):
                   users.stream()
                       .map(this::fetchFromDatabase)
                       .toList();
            
               INTERVIEW QUESTION:
                   "How to determine if parallel stream will improve performance?
                   What factors should you consider?"
            
               COMMON MISTAKES:
                   - Not benchmarking
                   - Assuming always faster
            
            ─────────────────────────────────────────────────────────────────────
            
            3. ORDER PRESERVATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ParallelStream may reorder elements.
            
               WHY IT EXISTS:
               - Performance optimization
               - Non-blocking processing
            
               SIMPLE EXAMPLE:
                   List<Integer> small = Arrays.asList(1, 2, 3, 4, 5);
                   
                   // Sequential forEach:
                   small.stream().forEach(n -> System.out.print(n + " "));
                   // Output: 1 2 3 4 5
                   
                   // Parallel forEach (order NOT guaranteed):
                   small.parallelStream().forEach(n -> System.out.print(n + " "));
                   // Output: 3 1 5 2 4 (may vary)
                   
                   // Parallel forEachOrdered (order preserved):
                   small.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
                   // Output: 1 2 3 4 5
            
               REAL-WORLD BACKEND EXAMPLE:
                   A log processing:
                   // Order matters:
                   logs.parallelStream()
                       .forEachOrdered(this::writeToLog);
                   
                   // Order doesn't matter:
                   metrics.parallelStream()
                       .forEach(this::updateMetric);
            
               INTERVIEW QUESTION:
                   "Does parallel stream preserve order?
                   How to maintain order in parallel processing?"
            
               COMMON MISTAKES:
                   - Assuming order is preserved
                   - Using forEachOrdered unnecessarily
            
            ─────────────────────────────────────────────────────────────────────
            
            4. THREAD POOL
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ParallelStream uses ForkJoinPool.commonPool by default.
            
               WHY IT EXISTS:
               - Shared thread pool
               - Resource efficiency
            
               SIMPLE EXAMPLE:
                   // Default:
                   // - Uses ForkJoinPool.commonPool()
                   // - Parallelism: CPU cores - 1
                   // - Can set: -Djava.util.concurrent.ForkJoinPool.common.parallelism=4
                   
                   // Custom pool:
                   ForkJoinPool customPool = new ForkJoinPool(4);
                   customPool.submit(() -> 
                       list.parallelStream().forEach(this::process)
                   ).join();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web application:
                   // Avoid common pool for blocking I/O:
                   ForkJoinPool ioPool = new ForkJoinPool(10);
                   ioPool.submit(() -> 
                       requests.parallelStream().map(this::callExternalApi)
                   ).join();
            
               INTERVIEW QUESTION:
                   "What thread pool does parallel stream use?
                   How to configure it?"
            
               COMMON MISTAKES:
                   - Blocking common pool
                   - Not understanding default parallelism
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Parallel streams are essential for:
            - Performance optimization
            - CPU-intensive processing
            - Large dataset handling
            - Understanding trade-offs
            """);
    }
}
