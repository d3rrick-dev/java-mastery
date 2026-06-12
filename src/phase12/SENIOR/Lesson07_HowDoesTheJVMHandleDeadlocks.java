package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle deadlocks?
 *
 * Difficulty: SENIOR
 */

public class Lesson07_HowDoesTheJVMHandleDeadlocks {
    public static void main(String[] args) {
        System.out.println("=== JVM DEADLOCK HANDLING ===\n");
        System.out.println("What is a Deadlock?");
        System.out.println("  - Two or more threads waiting forever for each other");
        System.out.println("  - Thread 1 holds Lock A, waits for Lock B");
        System.out.println("  - Thread 2 holds Lock B, waits for Lock A");
        System.out.println("  - Neither can proceed");
        System.out.println();
        System.out.println("JVM Detection:");
        System.out.println("  - jstack: prints thread dump with deadlock detection");
        System.out.println("  - jconsole: visual thread monitoring");
        System.out.println("  - ThreadMXBean.findDeadlockedThreads() API");
        System.out.println("  - -XX:+PrintGCDetails (includes deadlock info)");
        System.out.println();
        System.out.println("JVM Recovery:");
        System.out.println("  - JVM does NOT automatically recover from deadlock");
        System.out.println("  - Application must handle or restart");
        System.out.println("  - Threads remain in BLOCKED state indefinitely");
        System.out.println("  - Only solution: kill JVM or fix code");
        System.out.println();
        System.out.println("Prevention Strategies:");
        System.out.println("  - Lock ordering: always acquire locks in same order");
        System.out.println("  - Lock timeout: tryLock with timeout");
        System.out.println("  - Avoid nested locks");
        System.out.println("  - Use concurrent utilities (ConcurrentHashMap)");
        System.out.println("  - Open calls: release locks before calling external code");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Deadlocks are design bugs, not runtime bugs");
        System.out.println("  - Static analysis tools can detect potential deadlocks");
        System.out.println("  - Thread dumps are essential for diagnosis");
        System.out.println("  - Production systems need deadlock monitoring");
    }
}
