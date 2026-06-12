package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle thread stack dumps?
 *
 * Difficulty: SENIOR
 */

public class Lesson36_HowDoesTheJVMHandleThreadStackDumps {
    public static void main(String[] args) {
        System.out.println("=== THREAD STACK DUMPS IN JVM ===\n");
        System.out.println("What is a Thread Stack Dump?");
        System.out.println("  - Snapshot of all threads' call stacks");
        System.out.println("  - Shows what each thread is doing");
        System.out.println("  - Essential for debugging production issues");
        System.out.println();
        System.out.println("How to Get Stack Dump:");
        System.out.println("  - jstack <pid>");
        System.out.println("  - kill -3 <pid> (Unix/Linux)");
        System.out.println("  - Ctrl+Break (Windows)");
        System.out.println("  - ThreadMXBean.dumpAllThreads() API");
        System.out.println();
        System.out.println("What it Shows:");
        System.out.println("  - Thread name and state (RUNNABLE, BLOCKED, WAITING)");
        System.out.println("  - Call stack (method names and line numbers)");
        System.out.println("  - Lock information (locked, waiting to lock)");
        System.out.println("  - Deadlock detection (if any)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Stack dumps are taken at safepoints");
        System.out.println("  - May not show exact line if JIT compiled");
        System.out.println("  - Use -XX:+PrintSafepointStatistics for timing");
        System.out.println("  - Essential for diagnosing hangs and deadlocks");
    }
}
