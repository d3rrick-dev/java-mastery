package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT compilation?
 *
 * Difficulty: SENIOR
 */

public class Lesson25_HowDoesTheJVMHandleJITCompilation {
    public static void main(String[] args) {
        System.out.println("=== JIT COMPILATION IN JVM ===\n");
        System.out.println("What is JIT?");
        System.out.println("  - Just-In-Time compiler");
        System.out.println("  - Compiles bytecode to native machine code at runtime");
        System.out.println("  - Part of HotSpot JVM");
        System.out.println();
        System.out.println("JIT Compilation Levels:");
        System.out.println("  - C1 (Client): fast compilation, less optimization");
        System.out.println("  - C2 (Server): slower compilation, more optimization");
        System.out.println("  - Tiered compilation (Java 7+): C1 then C2");
        System.out.println();
        System.out.println("Compilation Process:");
        System.out.println("  1. Interpreter: executes bytecode initially");
        System.out.println("  2. Profiling: collects method usage statistics");
        System.out.println("  3. C1 compilation: hot methods compiled quickly");
        System.out.println("  4. C2 compilation: very hot methods optimized deeply");
        System.out.println("  5. Deoptimization: fallback to interpreter if needed");
        System.out.println();
        System.out.println("Optimizations:");
        System.out.println("  - Inlining: replace method call with body");
        System.out.println("  - Loop unrolling: reduce loop overhead");
        System.out.println("  - Dead code elimination: remove unused code");
        System.out.println("  - Escape analysis: stack allocation");
        System.out.println("  - Lock elision: remove unnecessary locks");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - JIT warmup time affects performance benchmarks");
        System.out.println("  - Use -XX:+PrintCompilation to see compilation");
        System.out.println("  - Use JITWatch for analysis");
        System.out.println("  - AOT compilation (jaotc) for faster startup");
    }
}
