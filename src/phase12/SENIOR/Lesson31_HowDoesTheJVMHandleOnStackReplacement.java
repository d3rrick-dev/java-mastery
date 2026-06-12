package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle On-Stack Replacement (OSR)?
 *
 * Difficulty: SENIOR
 */

public class Lesson31_HowDoesTheJVMHandleOnStackReplacement {
    public static void main(String[] args) {
        System.out.println("=== ON-STACK REPLACEMENT (OSR) ===\n");
        System.out.println("What is OSR?");
        System.out.println("  - Mechanism to replace interpreted code with compiled code");
        System.out.println("  - Happens while method is still executing");
        System.out.println("  - Allows long-running methods to benefit from JIT");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. Method starts in interpreter");
        System.out.println("  2. JIT compiles method (C1 or C2)");
        System.out.println("  3. At safepoint, interpreter frame replaced with compiled frame");
        System.out.println("  4. Execution continues with compiled code");
        System.out.println();
        System.out.println("When Used:");
        System.out.println("  - Loops that run many iterations");
        System.out.println("  - Methods with high invocation count");
        System.out.println("  - Hot methods identified by profiler");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - OSR compilation is different from normal compilation");
        System.out.println("  - OSR entry point is at loop back-edge");
        System.out.println("  - Use -XX:+PrintCompilation to see OSR entries");
        System.out.println("  - OSR can cause deoptimization if assumptions break");
    }
}
