package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle heap dumps?
 *
 * Difficulty: SENIOR
 */

public class Lesson37_HowDoesTheJVMHandleHeapDumps {
    public static void main(String[] args) {
        System.out.println("=== HEAP DUMPS IN JVM ===\n");
        System.out.println("What is a Heap Dump?");
        System.out.println("  - Snapshot of all objects in heap memory");
        System.out.println("  - Used for memory leak analysis");
        System.out.println("  - Contains class, fields, references");
        System.out.println();
        System.out.println("How to Generate:");
        System.out.println("  - jmap -dump:format=b,file=heap.hprof <pid>");
        System.out.println("  - -XX:+HeapDumpOnOutOfMemoryError (automatic)");
        System.out.println("  - jcmd <pid> GC.heap_dump filename.hprof");
        System.out.println("  - MemoryMXBean.dumpHeap() API");
        System.out.println();
        System.out.println("Heap Dump Analysis Tools:");
        System.out.println("  - Eclipse MAT (Memory Analyzer Tool)");
        System.out.println("  - VisualVM");
        System.out.println("  - jhat (deprecated)");
        System.out.println("  - YourKit, JProfiler");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Heap dumps can be large (GBs)");
        System.out.println("  - Use -XX:HeapDumpPath to control location");
        System.out.println("  - Analyze with MAT: Dominators, Leak Suspects");
        System.out.println("  - Production: use async heap dump to avoid impact");
    }
}
