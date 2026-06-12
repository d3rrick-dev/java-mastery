package phase8;

/**
 * LESSON 10: MEMORY MANAGEMENT
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Java automatically manages memory through garbage collection.
 * Objects are created in heap memory, and when no longer referenced,
 * the GC reclaims the memory.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Prevent memory leaks (manual free() is error-prone)
 * - Automatic reclamation of unused objects
 * - Reduce programmer burden
 * - Improve stability
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant tables:
 * - Customers (objects) sit at tables (memory)
 * - When customers leave (no references), tables are cleared
 * - New customers can use cleared tables
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Web application:
 * - Each request creates objects
 * - After request completes, objects become unreachable
 * - GC reclaims memory for next requests
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How does Java manage memory?"
 * Answer: Heap for objects, stack for primitives,
 *         GC automatically reclaims unused objects
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Memory leaks through static references
 * - Holding references longer than needed
 * - Not understanding GC pauses
 * - Ignoring memory warnings
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - GC pauses affect latency
 * - Heap size affects GC frequency
 * - Object creation has cost
 * - Memory leaks cause OOM
 */

public class Lesson10_MemoryManagement {

    public static void main(String[] args) throws Exception {
        System.out.println("=== MEMORY MANAGEMENT ===\n");

        // ============================================================
        // EXAMPLE 1: Memory areas
        // ============================================================
        System.out.println("--- Example 1: Memory Areas ---\n");

        System.out.println("JVM Memory:");
        System.out.println("  Heap: Objects (shared, GC'd)");
        System.out.println("  Stack: Method frames, primitives (per thread)");
        System.out.println("  Metaspace: Class metadata");
        System.out.println("  Direct Memory: Off-heap (NIO)");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Object lifecycle
        // ============================================================
        System.out.println("--- Example 2: Object Lifecycle ---\n");

        System.out.println("1. Created: new Object()");
        System.out.println("2. Referenced: variable points to it");
        System.out.println("3. Unreachable: no references");
        System.out.println("4. Collected: GC reclaims memory");
        System.out.println("5. Finalized: finalize() called (deprecated)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Memory leak example
        // ============================================================
        System.out.println("--- Example 3: Memory Leak ---\n");

        System.out.println("Memory leak through static collection:");
        System.out.println("  static List<Object> cache = new ArrayList<>();");
        System.out.println("  // Objects added but never removed");
        System.out.println("  // GC can't reclaim them");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Proper resource cleanup
        // ============================================================
        System.out.println("--- Example 4: Resource Cleanup ---\n");

        System.out.println("Use try-with-resources:");
        System.out.println("  try (InputStream is = new FileInputStream(file)) {");
        System.out.println("    // use stream");
        System.out.println("  } // automatically closed");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: WeakReference for caching
        // ============================================================
        System.out.println("--- Example 5: WeakReference ---\n");

        java.util.Map<String, byte[]> cache = new java.util.WeakHashMap<>();

        String key = "temp-key";
        cache.put(key, new byte[1024]);

        System.out.println("Cache size: " + cache.size());

        // Remove strong reference
        key = null;

        // Suggest GC
        System.gc();
        Thread.sleep(100);

        System.out.println("After GC, cache size: " + cache.size());
        System.out.println("(WeakReference allows GC to reclaim)");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Memory monitoring
        // ============================================================
        System.out.println("--- Example 6: Memory Monitoring ---\n");

        MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = mxBean.getHeapMemoryUsage();

        System.out.println("Heap usage:");
        System.out.println("  Used: " + heapUsage.getUsed() / 1024 / 1024 + " MB");
        System.out.println("  Max: " + heapUsage.getMax() / 1024 / 1024 + " MB");
        System.out.println("  Committed: " + heapUsage.getCommitted() / 1024 / 1024 + " MB");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Memory best practices
        // ============================================================
        System.out.println("--- Example 7: Best Practices ---\n");

        System.out.println("1. Avoid memory leaks:");
        System.out.println("   - Clear collections when done");
        System.out.println("   - Remove listeners/references");
        System.out.println("   - Use WeakReference for caches");
        System.out.println();
        System.out.println("2. Reduce object creation:");
        System.out.println("   - Reuse objects (object pools)");
        System.out.println("   - Use primitives instead of wrappers");
        System.out.println("   - Avoid creating in loops");
        System.out.println();
        System.out.println("3. Monitor memory:");
        System.out.println("   - Track heap usage");
        System.out.println("   - Monitor GC pauses");
        System.out.println("   - Set up alerts for OOM");
    }
}
