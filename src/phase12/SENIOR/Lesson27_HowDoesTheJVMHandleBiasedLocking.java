package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle biased locking?
 *
 * Difficulty: SENIOR
 */

public class Lesson27_HowDoesTheJVMHandleBiasedLocking {
    public static void main(String[] args) {
        System.out.println("=== BIASED LOCKING IN JVM ===\n");
        System.out.println("What is Biased Locking?");
        System.out.println("  - Optimization to reduce uncontended lock overhead");
        System.out.println("  - Biases lock toward first acquiring thread");
        System.out.println("  - Eliminates atomic operations for uncontended locks");
        System.out.println();
        System.out.println("Lock States:");
        System.out.println("  1. Unlocked: no owner");
        System.out.println("  2. Biased: biased toward thread (no CAS needed)");
        System.out.println("  3. Lightweight: thin lock (CAS, no OS involvement)");
        System.out.println("  4. Heavyweight: OS mutex (contended)");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - First acquisition: bias toward thread");
        System.out.println("  - Same thread reacquires: no atomic operation");
        System.out.println("  - Different thread: revocation, then rebias or inflate");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Significant performance improvement for single-threaded access");
        System.out.println("  - Disable with -XX:-UseBiasedLocking for microbenchmarks");
        System.out.println("  - Revocation has overhead in multi-threaded scenarios");
        System.out.println("  - Java 15+ deprecated, Java 19+ removed");
    }
}
