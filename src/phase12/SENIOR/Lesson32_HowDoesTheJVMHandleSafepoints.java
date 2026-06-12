package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle safepoints?
 *
 * Difficulty: SENIOR
 */

public class Lesson32_HowDoesTheJVMHandleSafepoints {
    public static void main(String[] args) {
        System.out.println("=== SAFEPOINTS IN JVM ===\n");
        System.out.println("What is a Safepoint?");
        System.out.println("  - Point in execution where all threads can be stopped");
        System.out.println("  - Used for GC, deoptimization, thread stack dumps");
        System.out.println("  - All threads must reach safepoint before operation");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - JVM requests safepoint (e.g., for GC)");
        System.out.println("  - Threads check safepoint poll at method calls/loops");
        System.out.println("  - Threads stop at next safepoint");
        System.out.println("  - Operation executes (GC, etc.)");
        System.out.println("  - Threads resume");
        System.out.println();
        System.out.println("Safepoint Poll:");
        System.out.println("  - Inserted at method calls, loop back-edges");
        System.out.println("  - Checks if safepoint requested");
        System.out.println("  - If yes, blocks until safepoint complete");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Safepoint synchronization causes stop-the-world pauses");
        System.out.println("  - Use -XX:+PrintSafepointStatistics to analyze");
        System.out.println("  - Long-running native methods can delay safepoints");
        System.out.println("  - Critical for GC pause time");
    }
}
