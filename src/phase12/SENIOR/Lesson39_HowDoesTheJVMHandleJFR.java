package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JFR (Java Flight Recorder)?
 *
 * Difficulty: SENIOR
 */

public class Lesson39_HowDoesTheJVMHandleJFR {
    public static void main(String[] args) {
        System.out.println("=== JAVA FLIGHT RECORDER (JFR) ===\n");
        System.out.println("What is JFR?");
        System.out.println("  - Low-overhead profiling and diagnostics");
        System.out.println("  - Built into JVM (Oracle/OpenJDK)");
        System.out.println("  - Records events: CPU, memory, GC, locks, I/O");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - Events emitted by JVM and application");
        System.out.println("  - Circular buffer in memory");
        System.out.println("  - Dump to file on demand or at end");
        System.out.println("  - Overhead: typically < 1%");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  - jcmd <pid> JFR.start name=recording");
        System.out.println("  - jcmd <pid> JFR.stop name=recording");
        System.out.println("  - jcmd <pid> JFR.check");
        System.out.println("  - Programmatic: FlightRecorder API");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Production-safe (low overhead)");
        System.out.println("  - Analyze with Java Mission Control (JMC)");
        System.out.println("  - Custom events for application metrics");
        System.out.println("  - Alternative to commercial profilers");
    }
}
