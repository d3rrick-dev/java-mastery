package phase8;

import java.lang.management.*;
import java.util.List;

/**
 * LESSON 11: GARBAGE COLLECTION FUNDAMENTALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Garbage Collection (GC) is Java's automatic memory management.
 * It finds objects that are no longer used and reclaims their memory.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Automatic memory reclamation
 * - Prevent memory leaks
 * - Reduce programmer burden
 * - Improve stability
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Cleaning crew in an office:
 * - Workers (threads) create trash (objects)
 * - When trash is no longer needed, cleaning crew (GC) removes it
 * - Office stays clean without workers worrying about trash
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Web server:
 * - Each request creates objects
 * - After request, objects become unreachable
 * - GC reclaims memory for next requests
 * - GC pauses affect response time
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How does garbage collection work?"
 * Answer: GC finds unreachable objects and reclaims memory,
 *         uses generational collection
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Calling System.gc() (usually bad idea)
 * - Finalizers (deprecated, unreliable)
 * - Assuming GC is free (it has cost)
 * - Not understanding GC pauses
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - GC pauses affect latency
 * - Heap size affects GC frequency
 * - Object creation rate affects GC pressure
 * - Different GC algorithms have different trade-offs
 */

public class Lesson11_GarbageCollectionFundamentals {

    public static void main(String[] args) throws Exception {
        System.out.println("=== GARBAGE COLLECTION FUNDAMENTALS ===\n");

        // ============================================================
        // EXAMPLE 1: GC generations
        // ============================================================
        System.out.println("--- Example 1: GC Generations ---\n");

        System.out.println("Generational GC:");
        System.out.println("  Young Generation (Eden + Survivor):");
        System.out.println("    - New objects allocated here");
        System.out.println("    - Minor GC (fast, frequent)");
        System.out.println();
        System.out.println("  Old Generation (Tenured):");
        System.out.println("    - Long-lived objects");
        System.out.println("    - Major GC (slow, infrequent)");
        System.out.println();
        System.out.println("  Metaspace:");
        System.out.println("    - Class metadata");
        System.out.println("    - Not garbage collected like heap");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Object aging
        // ============================================================
        System.out.println("--- Example 2: Object Aging ---\n");

        System.out.println("Object lifecycle in GC:");
        System.out.println("  1. Created in Eden");
        System.out.println("  2. Survives minor GC -> moves to Survivor");
        System.out.println("  3. Survives multiple GCs -> moves to Old Gen");
        System.out.println("  4. In Old Gen until unreachable");
        System.out.println("  5. Major GC reclaims Old Gen");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: GC types
        // ============================================================
        System.out.println("--- Example 3: GC Types ---\n");

        System.out.println("Minor GC:");
        System.out.println("  - Collects Young Generation");
        System.out.println("  - Fast (milliseconds)");
        System.out.println("  - Frequent");
        System.out.println("  - Uses copying algorithm");
        System.out.println();
        System.out.println("Major GC (Full GC):");
        System.out.println("  - Collects entire heap");
        System.out.println("  - Slow (seconds)");
        System.out.println("  - Infrequent");
        System.out.println("  - Causes 'stop-the-world' pause");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: GC algorithms
        // ============================================================
        System.out.println("--- Example 4: GC Algorithms ---\n");

        System.out.println("Serial GC: Single thread, simple");
        System.out.println("Parallel GC: Multiple threads, throughput");
        System.out.println("CMS: Concurrent, low pause (deprecated)");
        System.out.println("G1 GC: Balanced, predictable pauses");
        System.out.println("ZGC: Ultra-low pause (Java 11+)");
        System.out.println("Shenandoah: Ultra-low pause (OpenJDK)");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Triggering GC (for demo)
        // ============================================================
        System.out.println("--- Example 5: GC Monitoring ---\n");

        // Get GC beans
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        System.out.println("Garbage Collectors:");
        for (GarbageCollectorMXBean gc : gcBeans) {
            System.out.println("  " + gc.getName());
            System.out.println("    Count: " + gc.getCollectionCount());
            System.out.println("    Time: " + gc.getCollectionTime() + "ms");
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Memory before/after GC
        // ============================================================
        System.out.println("--- Example 6: Memory Before/After GC ---\n");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapBefore = memoryBean.getHeapMemoryUsage();

        System.out.println("Before GC:");
        System.out.println("  Used: " + heapBefore.getUsed() / 1024 / 1024 + " MB");

        // Create some garbage
//        for (int i = 0; i < 100_000; i++) {
//            new byte[1024];
//        }

        MemoryUsage heapAfter = memoryBean.getHeapMemoryUsage();
        System.out.println("After creating garbage:");
        System.out.println("  Used: " + heapAfter.getUsed() / 1024 / 1024 + " MB");

        // Suggest GC
        System.gc();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        MemoryUsage heapAfterGC = memoryBean.getHeapMemoryUsage();
        System.out.println("After GC:");
        System.out.println("  Used: " + heapAfterGC.getUsed() / 1024 / 1024 + " MB");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: GC best practices
        // ============================================================
        System.out.println("--- Example 7: GC Best Practices ---\n");

        System.out.println("1. Don't call System.gc() explicitly");
        System.out.println("2. Size heap appropriately");
        System.out.println("3. Choose right GC for your workload");
        System.out.println("4. Avoid finalizers and cleaners");
        System.out.println("5. Minimize object creation");
        System.out.println("6. Use object pools for expensive objects");
        System.out.println("7. Monitor GC in production");
        System.out.println("8. Set appropriate GC logging");
    }
}
