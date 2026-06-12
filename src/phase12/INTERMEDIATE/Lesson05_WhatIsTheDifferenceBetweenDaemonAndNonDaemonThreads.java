package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between daemon and non-daemon threads?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson05_WhatIsTheDifferenceBetweenDaemonAndNonDaemonThreads {
    public static void main(String[] args) {
        System.out.println("=== DAEMON VS NON-DAEMON THREADS ===\n");
        System.out.println("Daemon Thread:");
        System.out.println("  - Low-priority background thread");
        System.out.println("  - JVM exits when only daemon threads remain");
        System.out.println("  - setDaemon(true) before start()");
        System.out.println("  - Examples: GC, finalizer, signal dispatcher");
        System.out.println();
        System.out.println("Non-Daemon Thread (User Thread):");
        System.out.println("  - Normal priority thread");
        System.out.println("  - JVM waits for all non-daemon threads to finish");
        System.out.println("  - Default for all threads");
        System.out.println("  - Examples: main thread, business logic threads");
        System.out.println();
        System.out.println("Use daemon threads for: Background services, cleanup tasks");
        System.out.println("Use non-daemon threads for: Critical business logic");
    }
}
