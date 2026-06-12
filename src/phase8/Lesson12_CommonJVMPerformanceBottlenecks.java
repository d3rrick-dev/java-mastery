package phase8;

/**
 * LESSON 12: COMMON JVM PERFORMANCE BOTTLENECKS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * JVM performance bottlenecks are common issues that slow down
 * Java applications. Like traffic jams in a city - certain
 * spots always cause delays.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - GC pauses
 * - Memory leaks
 * - Thread contention
 * - Poor algorithm choices
 * - Inefficient data structures
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * E-commerce site:
 * - GC pauses cause checkout to freeze
 * - Memory leak causes OOM after 24 hours
 * - Thread contention slows down search
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API service:
 * - High CPU from inefficient algorithm
 * - Memory leak from static cache
 * - Thread pool exhaustion from blocking calls
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What are common JVM performance issues?"
 * Answer: GC pauses, memory leaks, thread contention,
 *         poor algorithms
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Ignoring GC logs
 * - Not monitoring memory usage
 * - Using wrong data structures
 * - Creating objects in loops
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - GC pauses: latency spikes
 * - Memory leaks: eventual OOM
 * - Thread contention: reduced throughput
 * - Poor algorithms: quadratic or worse
 */

public class Lesson12_CommonJVMPerformanceBottlenecks {

    public static void main(String[] args) throws Exception {
        System.out.println("=== COMMON JVM PERFORMANCE BOTTLENECKS ===\n");

        // ============================================================
        // EXAMPLE 1: GC pauses
        // ============================================================
        System.out.println("--- Example 1: GC Pauses ---\n");

        System.out.println("Symptoms:");
        System.out.println("  - Latency spikes");
        System.out.println("  - Application 'freezes' briefly");
        System.out.println("  - High CPU during GC");
        System.out.println();
        System.out.println("Solutions:");
        System.out.println("  - Tune heap size");
        System.out.println("  - Use G1/ZGC for low pauses");
        System.out.println("  - Reduce object allocation rate");
        System.out.println("  - Enable GC logging");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Memory leaks
        // ============================================================
        System.out.println("--- Example 2: Memory Leaks ---\n");

        System.out.println("Common causes:");
        System.out.println("  1. Static collections holding references");
        System.out.println("  2. Unclosed resources (streams, connections)");
        System.out.println("  3. Listeners not removed");
        System.out.println("  4. ThreadLocal not cleaned");
        System.out.println("  5. Caches without eviction");
        System.out.println();
        System.out.println("Detection:");
        System.out.println("  - Heap dump analysis");
        System.out.println("  - Monitor heap usage over time");
        System.out.println("  - Profile with VisualVM");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Thread contention
        // ============================================================
        System.out.println("--- Example 3: Thread Contention ---\n");

        System.out.println("Symptoms:");
        System.out.println("  - Low CPU utilization");
        System.out.println("  - Threads in BLOCKED state");
        System.out.println("  - High lock wait time");
        System.out.println();
        System.out.println("Solutions:");
        System.out.println("  - Reduce lock scope");
        System.out.println("  - Use concurrent collections");
        System.out.println("  - Consider lock-free algorithms");
        System.out.println("  - Use ReadWriteLock for read-heavy");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Poor algorithms
        // ============================================================
        System.out.println("--- Example 4: Poor Algorithms ---\n");

        System.out.println("Common issues:");
        System.out.println("  - O(n^2) instead of O(n log n)");
        System.out.println("  - Searching list instead of using HashMap");
        System.out.println("  - N+1 query problem");
        System.out.println("  - Unnecessary sorting");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Object creation overhead
        // ============================================================
        System.out.println("--- Example 5: Object Creation ---\n");

        System.out.println("Costs:");
        System.out.println("  - Object allocation: ~10ns");
        System.out.println("  - GC pressure increases");
        System.out.println("  - More young GCs");
        System.out.println();
        System.out.println("Solutions:");
        System.out.println("  - Reuse objects (pools)");
        System.out.println("  - Use primitives");
        System.out.println("  - Avoid creating in loops");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Monitoring tools
        // ============================================================
        System.out.println("--- Example 6: Monitoring Tools ---\n");

        System.out.println("Built-in:");
        System.out.println("  - jstat: GC statistics");
        System.out.println("  - jstack: Thread dumps");
        System.out.println("  - jmap: Heap dumps");
        System.out.println("  - jhat: Heap analysis");
        System.out.println();
        System.out.println("External:");
        System.out.println("  - VisualVM: Profiling");
        System.out.println("  - Java Flight Recorder: Production profiling");
        System.out.println("  - async-profiler: Low overhead");
        System.out.println("  - Prometheus + Grafana: Metrics");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Performance checklist
        // ============================================================
        System.out.println("--- Example 7: Performance Checklist ---\n");

        System.out.println("Before deployment:");
        System.out.println("  [ ] Profile CPU usage");
        System.out.println("  [ ] Profile memory allocation");
        System.out.println("  [ ] Check GC logs");
        System.out.println("  [ ] Load test");
        System.out.println("  [ ] Monitor thread states");
        System.out.println();
        System.out.println("In production:");
        System.out.println("  [ ] Monitor heap usage");
        System.out.println("  [ ] Track GC pause times");
        System.out.println("  [ ] Monitor response times");
        System.out.println("  [ ] Set up alerts");
        System.out.println("  [ ] Regular heap dumps");
    }
}
