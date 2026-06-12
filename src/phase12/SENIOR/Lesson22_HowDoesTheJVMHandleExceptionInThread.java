package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle uncaught exceptions in threads?
 *
 * Difficulty: SENIOR
 */

public class Lesson22_HowDoesTheJVMHandleExceptionInThread {
    public static void main(String[] args) {
        System.out.println("=== UNCAUGHT EXCEPTIONS IN THREADS ===\n");
        System.out.println("Default Behavior:");
        System.out.println("  - Uncaught exception terminates the thread");
        System.out.println("  - Stack trace printed to System.err");
        System.out.println("  - Other threads continue running");
        System.out.println("  - JVM does NOT terminate");
        System.out.println();
        System.out.println("Thread.UncaughtExceptionHandler:");
        System.out.println("  - Interface for handling uncaught exceptions");
        System.out.println("  - Method: void uncaughtException(Thread t, Throwable e)");
        System.out.println("  - Can be set per thread or globally");
        System.out.println("  - Example: thread.setUncaughtExceptionHandler(handler)");
        System.out.println();
        System.out.println("Global Handler:");
        System.out.println("  - Thread.setDefaultUncaughtExceptionHandler(handler)");
        System.out.println("  - Applies to all threads without specific handler");
        System.out.println("  - Useful for logging, cleanup, alerts");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Critical for production systems");
        System.out.println("  - Prevents silent thread failures");
        System.out.println("  - Can trigger alerts, metrics, graceful degradation");
        System.out.println("  - Spring Boot: @ControllerAdvice for web, Thread for async");
    }
}
