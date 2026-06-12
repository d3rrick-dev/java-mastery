package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT escape analysis?
 *
 * Difficulty: SENIOR
 */

public class Lesson42_HowDoesTheJVMHandleJITEscapeAnalysis {
    public static void main(String[] args) {
        System.out.println("=== JIT ESCAPE ANALYSIS IN JVM ===\n");
        System.out.println("What is Escape Analysis?");
        System.out.println("  - JIT analysis to determine object scope");
        System.out.println("  - If object doesn't escape method, allocate on stack");
        System.out.println("  - Eliminates heap allocation and GC pressure");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. Analyze object references");
        System.out.println("  2. Check if object escapes method/thread");
        System.out.println("  3. If no escape: stack allocation or scalar replacement");
        System.out.println("  4. If escape: normal heap allocation");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Reduced GC pressure");
        System.out.println("  - Better cache locality");
        System.out.println("  - Eliminates synchronization for non-escaped objects");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Enabled by default: -XX:+DoEscapeAnalysis");
        System.out.println("  - Works with -XX:+EliminateAllocations");
        System.out.println("  - Not all objects can be stack-allocated");
        System.out.println("  - Use -XX:+PrintEscapeAnalysis to see results");
    }
}
