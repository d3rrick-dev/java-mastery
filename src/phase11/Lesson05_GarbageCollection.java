package phase11;

/**
 * LESSON 5: GARBAGE COLLECTION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Garbage Collection (GC) automatically reclaims memory
 * from objects that are no longer used. Like a cleaning crew
 * that removes trash you no longer need.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Automatic memory management
 * - Prevents memory leaks
 * - No manual free() like in C/C++
 */

public class Lesson05_GarbageCollection {

    public static void main(String[] args) {
        System.out.println("=== GARBAGE COLLECTION ===\n");

        // ============================================================
        // EXAMPLE 1: GC eligibility
        // ============================================================
        System.out.println("--- Example 1: GC Eligibility ---\n");

        System.out.println("Object becomes eligible for GC when:");
        System.out.println("  1. No references point to it");
        System.out.println("  2. All references are null");
        System.out.println("  3. Reference goes out of scope");
        System.out.println();

        // Example: Object eligible for GC
        String s1 = new String("hello");
        s1 = null;  // Now eligible for GC
        System.out.println("s1 set to null - object eligible for GC");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: GC roots
        // ============================================================
        System.out.println("--- Example 2: GC Roots ---\n");

        System.out.println("GC starts from GC Roots:");
        System.out.println("  - Local variables on stack");
        System.out.println("  - Static variables in method area");
        System.out.println("  - JNI references");
        System.out.println("  - Thread references");
        System.out.println();
        System.out.println("Objects reachable from roots are NOT collected.");
        System.out.println("Objects NOT reachable are eligible for GC.");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: GC types
        // ============================================================
        System.out.println("--- Example 3: GC Types ---\n");

        System.out.println("Minor GC (Young Generation):");
        System.out.println("  - Collects Eden and Survivor spaces");
        System.out.println("  - Fast, frequent");
        System.out.println("  - Objects that survive move to Old Gen");
        System.out.println();
        System.out.println("Major GC (Old Generation):");
        System.out.println("  - Collects Old Gen");
        System.out.println("  - Slower, less frequent");
        System.out.println("  - Often triggers Full GC");
        System.out.println();
        System.out.println("Full GC:");
        System.out.println("  - Collects entire heap (Young + Old + Metaspace)");
        System.out.println("  - Stop-the-world (application pauses)");
        System.out.println("  - Should be minimized");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: GC algorithms
        // ============================================================
        System.out.println("--- Example 4: GC Algorithms ---\n");

        System.out.println("Serial GC (-XX:+UseSerialGC):");
        System.out.println("  - Single-threaded");
        System.out.println("  - For small applications");
        System.out.println("  - Simple, low overhead");
        System.out.println();
        System.out.println("Parallel GC (-XX:+UseParallelGC):");
        System.out.println("  - Multi-threaded young gen");
        System.out.println("  - Throughput focused");
        System.out.println("  - Default in Java 8");
        System.out.println();
        System.out.println("G1 GC (-XX:+UseG1GC):");
        System.out.println("  - Default in Java 9+");
        System.out.println("  - Region-based");
        System.out.println("  - Predictable pause times");
        System.out.println();
        System.out.println("ZGC (-XX:+UseZGC):");
        System.out.println("  - Low latency (sub-millisecond pauses)");
        System.out.println("  - Concurrent");
        System.out.println("  - Java 11+");
        System.out.println();
    }

    // ============================================================
    // GC DETAILS
    // ============================================================
    /*
     * Garbage Collection:
     *
     * 1. Mark: Find reachable objects from GC roots
     * 2. Sweep: Remove unreachable objects
     * 3. Compact: Move objects together (optional)
     *
     * Generational Hypothesis:
     * - Most objects die young
     * - Few objects survive to old generation
     *
     * Young Generation:
     * - Eden: New objects allocated here
     * - Survivor 0/1: Objects that survived minor GC
     * - Tenuring: Objects promoted to Old Gen after surviving N GCs
     *
     * GC Roots:
     * - Local variables and method parameters
     * - Active threads
     * - Static variables
     * - JNI references
     */
}
