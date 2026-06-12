package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JVMPI and JVMTI?
 *
 * Difficulty: SENIOR
 */

public class Lesson38_HowDoesTheJVMHandleJVMPIAndJVMTI {
    public static void main(String[] args) {
        System.out.println("=== JVMPI AND JVMTI IN JVM ===\n");
        System.out.println("JVMPI (Java Virtual Machine Profiler Interface):");
        System.out.println("  - Old profiling interface (Java 1.3-5)");
        System.out.println("  - Deprecated and removed");
        System.out.println("  - Replaced by JVMTI");
        System.out.println();
        System.out.println("JVMTI (Java Virtual Machine Tool Interface):");
        System.out.println("  - Native interface for JVM tools");
        System.out.println("  - Used by profilers, debuggers, agents");
        System.out.println("  - Provides: heap walking, thread control, breakpoints");
        System.out.println("  - Introduced in Java 5, still current");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - Agent library loaded via -agentlib or -agentpath");
        System.out.println("  - Agent registers event callbacks");
        System.out.println("  - JVM calls agent on events (method entry, GC, etc.)");
        System.out.println("  - Agent can query/modify JVM state");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Used by profilers: VisualVM, YourKit, JProfiler");
        System.out.println("  - Used by debuggers: IntelliJ, Eclipse");
        System.out.println("  - Can impact performance (overhead)");
        System.out.println("  - Java agents for AOP, monitoring, security");
    }
}
