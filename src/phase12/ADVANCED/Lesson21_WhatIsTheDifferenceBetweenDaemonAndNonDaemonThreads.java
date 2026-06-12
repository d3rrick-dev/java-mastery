package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between daemon and non-daemon threads?
 *
 * Difficulty: ADVANCED
 */

public class Lesson21_WhatIsTheDifferenceBetweenDaemonAndNonDaemonThreads {
    public static void main(String[] args) {
        System.out.println("=== DAEMON VS NON-DAEMON THREADS ===\n");
        System.out.println("Daemon Thread:");
        System.out.println("  - Low-priority thread that runs in background");
        System.out.println("  - JVM exits when only daemon threads remain");
        System.out.println("  - Used for background services (GC, finalizer, signal dispatcher)");
        System.out.println("  - Must be set before thread starts: setDaemon(true)");
        System.out.println("  - Cannot be changed after thread.start()");
        System.out.println();
        System.out.println("Non-Daemon Thread (User Thread):");
        System.out.println("  - High-priority thread that JVM waits for");
        System.out.println("  - JVM waits for all non-daemon threads to finish");
        System.out.println("  - Main thread is non-daemon");
        System.out.println("  - Default for all threads");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - GC thread: daemon (JVM exits even if GC is running)");
        System.out.println("  - Main thread: non-daemon (JVM waits for it)");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Daemon threads are for background services");
        System.out.println("  - Non-daemon threads are for critical work");
        System.out.println("  - JVM won't exit if non-daemon threads are running");
    }
}
