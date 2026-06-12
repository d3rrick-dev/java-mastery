package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle OutOfMemoryError?
 *
 * Difficulty: SENIOR
 */

public class Lesson09_HowDoesTheJVMHandleOutOfMemoryError {
    public static void main(String[] args) {
        System.out.println("=== OUTOFMEMORYERROR HANDLING ===\n");
        System.out.println("Types of OOM:");
        System.out.println("  - Java heap space: insufficient heap memory");
        System.out.println("  - GC overhead limit exceeded: GC takes too long");
        System.out.println("  - Metaspace: insufficient class metadata space");
        System.out.println("  - Unable to create new native thread: too many threads");
        System.out.println("  - Direct buffer memory: insufficient native memory");
        System.out.println();
        System.out.println("JVM Behavior:");
        System.out.println("  - Throws Error (not Exception)");
        System.out.println("  - Error is thrown in the thread that caused it");
        System.out.println("  - Other threads continue running");
        System.out.println("  - JVM does NOT terminate automatically");
        System.out.println("  - Application can catch and handle (not recommended)");
        System.out.println();
        System.out.println("Diagnosis:");
        System.out.println("  - Heap dump: -XX:+HeapDumpOnOutOfMemoryError");
        System.out.println("  - jmap: generate heap dump");
        System.out.println("  - jhat: analyze heap dump");
        System.out.println("  - VisualVM: visual analysis");
        System.out.println("  - Eclipse MAT: memory analysis tool");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Memory leaks (unclosed resources, static collections)");
        System.out.println("  - Large object allocation");
        System.out.println("  - Insufficient heap size (-Xmx)");
        System.out.println("  - Too many threads");
        System.out.println("  - Direct buffer overuse");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - OOM is a symptom, not the problem");
        System.out.println("  - Heap dump analysis is critical for root cause");
        System.out.println("  - Production: use -XX:+HeapDumpOnOutOfMemoryError");
        System.out.println("  - Consider using -XX:OnOutOfMemoryError for alerts");
    }
}
