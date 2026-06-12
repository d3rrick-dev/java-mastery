package phase11;

/**
 * LESSON 6: MEMORY LEAKS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Memory leak happens when objects are no longer needed
 * but still referenced, preventing GC from collecting them.
 * Like keeping old keys you don't need anymore.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Unintentional object retention
 * - Static collections growing indefinitely
 * - Unclosed resources
 * - Inner class references
 */

public class Lesson06_MemoryLeaks {

    public static void main(String[] args) {
        System.out.println("=== MEMORY LEAKS ===\n");

        // ============================================================
        // EXAMPLE 1: Static collection leak
        // ============================================================
        System.out.println("--- Example 1: Static Collection Leak ---\n");

        System.out.println("Bad: Static Map growing forever");
        System.out.println("  static Map<Long, Object> cache = new HashMap<>();");
        System.out.println("  // Never cleared, grows with every request");
        System.out.println();
        System.out.println("Good: Use WeakHashMap or limit size");
        System.out.println("  Map<Long, Object> cache = new WeakHashMap<>();");
        System.out.println("  // Entries removed when key no longer referenced");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Unclosed resources
        // ============================================================
        System.out.println("--- Example 2: Unclosed Resources ---\n");

        System.out.println("Bad: Connection not closed");
        System.out.println("  Connection conn = dataSource.getConnection();");
        System.out.println("  // conn never closed - resource leak");
        System.out.println();
        System.out.println("Good: Use try-with-resources");
        System.out.println("  try (Connection conn = dataSource.getConnection()) {");
        System.out.println("    // Auto-closed");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Inner class leak
        // ============================================================
        System.out.println("--- Example 3: Inner Class Leak ---\n");

        System.out.println("Non-static inner class holds implicit reference to outer class");
        System.out.println();
        System.out.println("Bad:");
        System.out.println("  class Outer {");
        System.out.println("    class Inner {  // Implicitly references Outer");
        System.out.println("      void process() { ... }");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();
        System.out.println("Good: Use static inner class");
        System.out.println("  class Outer {");
        System.out.println("    static class Inner {  // No outer reference");
        System.out.println("      void process() { ... }");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: ThreadLocal leak
        // ============================================================
        System.out.println("--- Example 4: ThreadLocal Leak ---\n");

        System.out.println("ThreadLocal values persist for thread lifetime");
        System.out.println();
        System.out.println("In thread pools:");
        System.out.println("  - Threads are reused");
        System.out.println("  - ThreadLocal values not cleared");
        System.out.println("  - Memory leak over time");
        System.out.println();
        System.out.println("Solution:");
        System.out.println("  threadLocal.remove();  // Always clean up");
        System.out.println();
    }

    // ============================================================
    // MEMORY LEAK TYPES
    // ============================================================
    /*
     * Common Memory Leak Causes:
     *
     * 1. Static Collections
     *    - Static maps/lists growing indefinitely
     *    - Solution: Use WeakHashMap, limit size, clear periodically
     *
     * 2. Unclosed Resources
     *    - Connections, streams, files
     *    - Solution: try-with-resources
     *
     * 3. Inner Classes
     *    - Non-static inner classes hold outer reference
     *    - Solution: Use static inner classes
     *
     * 4. ThreadLocal
     *    - Values persist in thread pools
     *    - Solution: Always call remove()
     *
     * 5. Listeners/Callbacks
     *    - Registered but never unregistered
     *    - Solution: Unregister when done
     *
     * 6. Caches
     *    - Growing without bounds
     *    - Solution: Use LRU cache, size limits
     */
}
