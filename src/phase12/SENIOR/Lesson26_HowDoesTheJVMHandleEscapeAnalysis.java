package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle escape analysis?
 *
 * Difficulty: SENIOR
 */

public class Lesson26_HowDoesTheJVMHandleEscapeAnalysis {
    public static void main(String[] args) {
        System.out.println("=== ESCAPE ANALYSIS IN JVM ===\n");
        System.out.println("What is Escape Analysis?");
        System.out.println("  - JIT optimization technique");
        System.out.println("  - Determines if object escapes current scope/thread");
        System.out.println("  - Enables stack allocation and lock elimination");
        System.out.println();
        System.out.println("Types of Escape:");
        System.out.println("  - Global escape: object accessible from other threads");
        System.out.println("  - Arg escape: object passed as argument to other methods");
        System.out.println("  - No escape: object confined to current method");
        System.out.println();
        System.out.println("Optimizations Enabled:");
        System.out.println("  - Stack allocation: allocate on stack instead of heap");
        System.out.println("  - Scalar replacement: break object into fields");
        System.out.println("  - Lock elimination: remove unnecessary synchronization");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - class Point { int x, y; }");
        System.out.println("  - Point p = new Point(1, 2); // doesn't escape");
        System.out.println("  - JVM allocates on stack, no GC needed");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Reduces GC pressure significantly");
        System.out.println("  - Use -XX:+DoEscapeAnalysis to enable (default in Java 6+)");
        System.out.println("  - Use -XX:+PrintEscapeAnalysis to see results");
        System.out.println("  - Not all objects can be stack-allocated");
    }
}
