package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle adaptive spinning?
 *
 * Difficulty: SENIOR
 */

public class Lesson35_HowDoesTheJVMHandleAdaptiveSpinning {
    public static void main(String[] args) {
        System.out.println("=== ADAPTIVE SPINNING IN JVM ===\n");
        System.out.println("What is Adaptive Spinning?");
        System.out.println("  - Optimization where thread spins instead of blocking");
        System.out.println("  - Waits for lock to be released without OS context switch");
        System.out.println("  - Adaptive: adjusts spin count based on history");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - Thread tries to acquire lock");
        System.out.println("  - If unavailable, spin for N iterations");
        System.out.println("  - If still unavailable, block (OS mutex)");
        System.out.println("  - Spin count adapts based on past success");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Avoids expensive context switch");
        System.out.println("  - Good for short lock hold times");
        System.out.println("  - Reduces latency for uncontended locks");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Wastes CPU if lock held long");
        System.out.println("  - Use -XX:+UseSpinning to enable");
        System.out.println("  - Java 7+ uses adaptive spinning by default");
        System.out.println("  - Not effective for high contention");
    }
}
