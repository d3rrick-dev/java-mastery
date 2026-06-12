package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle Thread-Local Allocation Buffers (TLAB)?
 *
 * Difficulty: SENIOR
 */

public class Lesson33_HowDoesTheJVMHandleThreadLocalAllocationBuffers {
    public static void main(String[] args) {
        System.out.println("=== THREAD-LOCAL ALLOCATION BUFFERS (TLAB) ===\n");
        System.out.println("What is TLAB?");
        System.out.println("  - Thread-local region in Eden space for fast allocation");
        System.out.println("  - Each thread gets its own TLAB");
        System.out.println("  - Eliminates synchronization for object allocation");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. JVM allocates TLAB for each thread in Eden");
        System.out.println("  2. Thread allocates objects in its TLAB (bump pointer)");
        System.out.println("  3. No locking needed (thread-local)");
        System.out.println("  4. When TLAB full, get new TLAB from Eden");
        System.out.println("  5. If Eden full, trigger GC");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Fast allocation (no CAS, no locking)");
        System.out.println("  - Reduces contention in multi-threaded apps");
        System.out.println("  - Better cache locality");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - TLAB size affects allocation performance");
        System.out.println("  - Use -XX:+PrintTLAB to see TLAB usage");
        System.out.println("  - Waste at end of TLAB (fragmentation)");
        System.out.println("  - Refill TLAB when threshold reached");
    }
}
