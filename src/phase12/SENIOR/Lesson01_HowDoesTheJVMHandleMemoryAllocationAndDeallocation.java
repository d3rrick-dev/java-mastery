package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle memory allocation and deallocation?
 *
 * Difficulty: SENIOR
 */

public class Lesson01_HowDoesTheJVMHandleMemoryAllocationAndDeallocation {
    public static void main(String[] args) {
        System.out.println("=== JVM MEMORY ALLOCATION AND DEALLOCATION ===\n");
        System.out.println("Memory Allocation:");
        System.out.println("  - Stack: automatic, LIFO, fast");
        System.out.println("  - Heap: dynamic, slower, GC-managed");
        System.out.println("  - Young Generation: Eden + 2 Survivor spaces");
        System.out.println("  - Old Generation: long-lived objects");
        System.out.println("  - Metaspace: class metadata (permgen in Java 7-)");
        System.out.println();
        System.out.println("Allocation Process:");
        System.out.println("  1. TLAB (Thread-Local Allocation Buffer) for fast allocation");
        System.out.println("  2. Bump-the-pointer in Eden space");
        System.out.println("  3. If Eden full, trigger Minor GC");
        System.out.println("  4. Surviving objects move to Survivor space");
        System.out.println("  5. Long-lived objects promoted to Old Generation");
        System.out.println();
        System.out.println("Deallocation (Garbage Collection):");
        System.out.println("  - Minor GC: Young Generation (frequent, fast)");
        System.out.println("  - Major GC: Old Generation (infrequent, slow)");
        System.out.println("  - Full GC: entire heap (stop-the-world)");
        System.out.println("  - G1GC: region-based, predictable pauses");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Object allocation rate affects GC frequency");
        System.out.println("  - Object promotion rate affects Old Gen pressure");
        System.out.println("  - TLAB reduces contention in multi-threaded apps");
        System.out.println("  - Escape analysis enables stack allocation");
    }
}
