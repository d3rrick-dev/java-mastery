package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT compilation?
 *
 * Difficulty: SENIOR
 */

public class Lesson40_HowDoesTheJVMHandleJITCompilation {
    public static void main(String[] args) {
        System.out.println("=== JIT COMPILATION IN JVM ===\n");
        System.out.println("What is JIT?");
        System.out.println("  - Just-In-Time compiler");
        System.out.println("  - Converts bytecode to native machine code");
        System.out.println("  - Happens at runtime, not ahead-of-time");
        System.out.println();
        System.out.println("JIT Compilation Levels:");
        System.out.println("  - C1 (Client): Fast compilation, less optimization");
        System.out.println("  - C2 (Server): Slow compilation, more optimization");
        System.out.println("  - Tiered: C1 first, then C2 for hot methods");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. Interpreter executes bytecode");
        System.out.println("  2. Profiler counts method invocations");
        System.out.println("  3. Hot methods compiled to native code");
        System.out.println("  4. Compiled code cached for reuse");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - JIT can inline methods, eliminate bounds checks");
        System.out.println("  - Escape analysis for stack allocation");
        System.out.println("  - Deoptimization: revert compiled code to interpreter");
        System.out.println("  - Use -XX:+PrintCompilation to see JIT activity");
    }
}
