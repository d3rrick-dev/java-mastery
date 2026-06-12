package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT Thread-Local Allocation Buffers (TLAB)?
 *
 * Difficulty: SENIOR
 */

public class Lesson46_HowDoesTheJVMHandleJITThreadLocalAllocationBuffers {
    public static void main(String[] args) {
        System.out.println("=== JIT THREAD-LOCAL ALLOCATION BUFFERS (TLAB) ===\n");
        System.out.println("What is TLAB?");
        System.out.println("  - Thread-local heap allocation buffer");
        System.out.println("  - Each thread gets small Eden chunk");
        System.out.println("  - Allocations are thread-safe without synchronization");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. Thread requests TLAB from Eden");
        System.out.println("  2. Allocates objects in TLAB ( bump pointer )");
        System.out.println("  3. TLAB fills up or GC happens");
        System.out.println("  4. New TLAB allocated");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Fast allocation (no lock contention)");
        System.out.println("  - Better cache locality");
        System.out.println("  - Reduced GC pressure");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - TLAB size: -XX:+PrintTLAB");
        System.out.println("  - Waste: unused space at end of TLAB");
        System.out.println("  - Refill waste: thread gets new TLAB before old full");
        System.out.println("  - Works with escape analysis for stack allocation");
    }
}
