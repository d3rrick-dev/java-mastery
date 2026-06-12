package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT lock coarsening?
 *
 * Difficulty: SENIOR
 */

public class Lesson48_HowDoesTheJVMHandleJITLockCoarsening {
    public static void main(String[] args) {
        System.out.println("=== JIT LOCK COARSENING IN JVM ===\n");
        System.out.println("What is Lock Coarsening?");
        System.out.println("  - JIT optimization to merge adjacent lock regions");
        System.out.println("  - Reduces lock/unlock overhead");
        System.out.println("  - Combines multiple synchronized blocks");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - JIT detects consecutive lock/unlock on same object");
        System.out.println("  - Merges into single larger lock region");
        System.out.println("  - Example: lock A, unlock A, lock A, unlock A -> lock A, unlock A");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Fewer lock acquisitions");
        System.out.println("  - Better CPU cache utilization");
        System.out.println("  - Reduced contention");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Opposite of lock elision (escape analysis)");
        System.out.println("  - Trade-off: larger critical section vs fewer locks");
        System.out.println("  - Can hurt concurrency if over-applied");
        System.out.println("  - Use -XX:+PrintEscapeAnalysis to see related optimizations");
    }
}
