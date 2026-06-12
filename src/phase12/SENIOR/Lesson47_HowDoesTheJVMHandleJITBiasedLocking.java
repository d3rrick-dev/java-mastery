package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT biased locking?
 *
 * Difficulty: SENIOR
 */

public class Lesson47_HowDoesTheJVMHandleJITBiasedLocking {
    public static void main(String[] args) {
        System.out.println("=== JIT BIASED LOCKING IN JVM ===\n");
        System.out.println("What is Biased Locking?");
        System.out.println("  - Optimization for uncontended locks");
        System.out.println("  - Avoids atomic operations on lock acquisition");
        System.out.println("  - 'Biases' lock toward first acquiring thread");
        System.out.println();
        System.out.println("Lock States:");
        System.out.println("  1. Unlocked");
        System.out.println("  2. Biased (no atomic CAS needed)");
        System.out.println("  3. Thin lock (lightweight, CAS)");
        System.out.println("  4. Fat lock (heavyweight, OS mutex)");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - First thread gets bias (mark word updated)");
        System.out.println("  - Same thread reacquires without CAS");
        System.out.println("  - Different thread causes revocation");
        System.out.println("  - Revoked lock goes to thin lock");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Enabled by default: -XX:+UseBiasedLocking");
        System.out.println("  - Disable for highly contended locks");
        System.out.println("  - Revocation has cost (safepoint)");
        System.out.println("  - Use -XX:+PrintBiasedLockingStatistics to see");
    }
}
