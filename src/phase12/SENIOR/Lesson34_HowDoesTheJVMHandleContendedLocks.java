package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle contended locks?
 *
 * Difficulty: SENIOR
 */

public class Lesson34_HowDoesTheJVMHandleContendedLocks {
    public static void main(String[] args) {
        System.out.println("=== CONTENDED LOCKS IN JVM ===\n");
        System.out.println("What is Lock Contention?");
        System.out.println("  - Multiple threads competing for same lock");
        System.out.println("  - Threads block waiting for lock to be released");
        System.out.println("  - Causes performance degradation");
        System.out.println();
        System.out.println("JVM Lock States:");
        System.out.println("  1. Uncontended: single thread, no waiting");
        System.out.println("  2. Lightly contended: brief waiting");
        System.out.println("  3. Heavily contended: long waiting, many threads");
        System.out.println();
        System.out.println("JVM Handling:");
        System.out.println("  - Biased locking: for uncontended (single thread)");
        System.out.println("  - Thin locking: for lightly contended (CAS)");
        System.out.println("  - Fat locking: for heavily contended (OS mutex)");
        System.out.println("  - Adaptive spinning: spin before blocking");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Contention is major scalability bottleneck");
        System.out.println("  - Use lock-free data structures (Atomic classes)");
        System.out.println("  - Use ConcurrentHashMap instead of synchronized HashMap");
        System.out.println("  - Profile with -XX:+PrintSafepointStatistics");
    }
}
