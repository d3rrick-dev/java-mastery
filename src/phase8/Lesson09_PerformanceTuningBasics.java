package phase8;

import java.util.concurrent.*;

/**
 * LESSON 9: PERFORMANCE TUNING BASICS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Performance tuning is optimizing your Java application to run
 * faster, use less memory, and handle more load. Like tuning a
 * car engine for better performance.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Applications need to handle more users
 * - Reduce infrastructure costs
 * - Improve user experience
 * - Meet SLAs
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * E-commerce site:
 * - Before tuning: 100 req/s, 2s response time
 * - After tuning: 1000 req/s, 200ms response time
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API service:
 * - Profile to find bottlenecks
 * - Optimize hot paths
 * - Tune JVM settings
 * - Monitor in production
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How do you approach performance tuning?"
 * Answer: Measure, identify bottleneck, optimize, repeat
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Premature optimization
 * - Optimizing without measuring
 * - Ignoring algorithmic complexity
 * - Not considering trade-offs
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Algorithm choice matters most (O(n) vs O(n^2))
 * - Data structure choice matters
 * - JVM tuning has smaller impact
 * - Profile before optimizing
 */

public class Lesson09_PerformanceTuningBasics {

    public static void main(String[] args) throws Exception {
        System.out.println("=== PERFORMANCE TUNING BASICS ===\n");

        // ============================================================
        // EXAMPLE 1: Measure first
        // ============================================================
        System.out.println("--- Example 1: Measure First ---\n");

        // Bad: O(n^2) - nested loop
        long start = System.nanoTime();
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i] += j;
            }
        }
        long badTime = System.nanoTime() - start;

        // Good: O(n) - single loop
        start = System.nanoTime();
        int[] arr2 = new int[1000];
        int sum = 0;
        for (int i = 0; i < arr2.length; i++) {
            sum += i;
            arr2[i] = sum;
        }
        long goodTime = System.nanoTime() - start;

        System.out.println("O(n^2) time: " + badTime / 1_000_000 + "ms");
        System.out.println("O(n) time: " + goodTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) badTime / goodTime) + "x\n");

        // ============================================================
        // EXAMPLE 2: Choose right data structure
        // ============================================================
        System.out.println("--- Example 2: Right Data Structure ---\n");

        int size = 100_000;

        // ArrayList: O(1) get, O(n) contains
        java.util.List<Integer> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) arrayList.add(i);

        start = System.nanoTime();
        boolean contains1 = arrayList.contains(size - 1);
        long arrayListTime = System.nanoTime() - start;

        // HashSet: O(1) contains
        java.util.Set<Integer> hashSet = new java.util.HashSet<>(arrayList);

        start = System.nanoTime();
        boolean contains2 = hashSet.contains(size - 1);
        long hashSetTime = System.nanoTime() - start;

        System.out.println("ArrayList contains: " + arrayListTime / 1_000_000 + "ms");
        System.out.println("HashSet contains: " + hashSetTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) arrayListTime / hashSetTime) + "x\n");

        // ============================================================
        // EXAMPLE 3: StringBuilder vs String concatenation
        // ============================================================
        System.out.println("--- Example 3: StringBuilder ---\n");

        int iterations = 10_000;

        // Bad: String concatenation in loop
        start = System.nanoTime();
        String bad = "";
        for (int i = 0; i < iterations; i++) {
            bad += i;
        }
        badTime = System.nanoTime() - start;

        // Good: StringBuilder
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }
        String good = sb.toString();
        goodTime = System.nanoTime() - start;

        System.out.println("String concat: " + badTime / 1_000_000 + "ms");
        System.out.println("StringBuilder: " + goodTime / 1_000_000 + "ms");
        System.out.println("Speedup: " + String.format("%.2f", (double) badTime / goodTime) + "x\n");

        // ============================================================
        // EXAMPLE 4: Thread pool sizing
        // ============================================================
        System.out.println("--- Example 4: Thread Pool Sizing ---\n");

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Cores: " + cores);

        // Test different pool sizes
        int[] sizes = {1, 2, 4, 8, 16};
        for (int size2 : sizes) {
            ExecutorService executor = Executors.newFixedThreadPool(size2);
            start = System.nanoTime();
            List<Future<?>> futures = new java.util.ArrayList<>();
            for (int i = 0; i < 100; i++) {
                futures.add(executor.submit(() -> {
                    try { Thread.sleep(10); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }));
            }
            for (Future<?> f : futures) f.get();
            long time = System.nanoTime() - start;
            System.out.println("Pool size " + size2 + ": " + time / 1_000_000 + "ms");
            executor.shutdown();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Memory optimization
        // ============================================================
        System.out.println("--- Example 5: Memory Optimization ---\n");

        System.out.println("Memory tips:");
        System.out.println("1. Use primitives instead of wrappers");
        System.out.println("2. Avoid creating objects in loops");
        System.out.println("3. Use object pools for expensive objects");
        System.out.println("4. Clear collections when no longer needed");
        System.out.println("5. Use appropriate collection initial capacity");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: JVM tuning flags
        // ============================================================
        System.out.println("--- Example 6: JVM Tuning ---\n");

        System.out.println("Common JVM flags:");
        System.out.println("-Xmx4g: Max heap size");
        System.out.println("-Xms2g: Initial heap size");
        System.out.println("-XX:+UseG1GC: G1 garbage collector");
        System.out.println("-XX:MaxGCPauseMillis=200: Target pause time");
        System.out.println("-XX:+UseStringDeduplication: Reduce String memory");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Profiling tools
        // ============================================================
        System.out.println("--- Example 7: Profiling Tools ---\n");

        System.out.println("Java profiling tools:");
        System.out.println("1. JVisualVM: Built-in, basic profiling");
        System.out.println("2. JConsole: Monitoring");
        System.out.println("3. Java Flight Recorder: Low overhead");
        System.out.println("4. async-profiler: CPU and allocation profiling");
        System.out.println("5. YourKit: Commercial, powerful");
        System.out.println();

        // ============================================================
        // EXAMPLE 8: Performance checklist
        // ============================================================
        System.out.println("--- Example 8: Performance Checklist ---\n");

        System.out.println("Before optimizing:");
        System.out.println("  [ ] Profile to find actual bottleneck");
        System.out.println("  [ ] Measure baseline performance");
        System.out.println("  [ ] Set performance goals");
        System.out.println();
        System.out.println("During optimization:");
        System.out.println("  [ ] Optimize algorithms first (biggest impact)");
        System.out.println("  [ ] Choose right data structures");
        System.out.println("  [ ] Reduce object creation");
        System.out.println("  [ ] Tune thread pools");
        System.out.println("  [ ] Consider caching");
        System.out.println();
        System.out.println("After optimization:");
        System.out.println("  [ ] Measure improvement");
        System.out.println("  [ ] Verify correctness");
        System.out.println("  [ ] Monitor in production");
    }
}
