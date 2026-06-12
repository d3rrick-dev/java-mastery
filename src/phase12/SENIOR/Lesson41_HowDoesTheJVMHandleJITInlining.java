package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT inlining?
 *
 * Difficulty: SENIOR
 */

public class Lesson41_HowDoesTheJVMHandleJITInlining {
    public static void main(String[] args) {
        System.out.println("=== JIT INLINING IN JVM ===\n");
        System.out.println("What is Inlining?");
        System.out.println("  - Replacing method call with method body");
        System.out.println("  - Eliminates call overhead");
        System.out.println("  - Enables further optimizations");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - JIT identifies hot methods");
        System.out.println("  - Small methods inlined aggressively");
        System.out.println("  - Large methods may be inlined if hot enough");
        System.out.println("  - Inlining threshold: -XX:MaxInlineSize, -XX:FreqInlineSize");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  - Eliminates method call overhead");
        System.out.println("  - Enables constant propagation");
        System.out.println("  - Enables dead code elimination");
        System.out.println("  - Better register allocation");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Final methods easier to inline");
        System.out.println("  - Private methods always inlinable");
        System.out.println("  - Polymorphic calls harder to inline");
        System.out.println("  - Use -XX:+PrintInlining to see inlining decisions");
    }
}
