package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT On-Stack Replacement (OSR)?
 *
 * Difficulty: SENIOR
 */

public class Lesson45_HowDoesTheJVMHandleJITOnStackReplacement {
    public static void main(String[] args) {
        System.out.println("=== JIT ON-STACK REPLACEMENT (OSR) ===\n");
        System.out.println("What is OSR?");
        System.out.println("  - Replacing interpreter frame with compiled code");
        System.out.println("  - Happens during method execution, not at entry");
        System.out.println("  - Allows long-running methods to benefit from JIT");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. Method running in interpreter");
        System.out.println("  2. JIT compiles method (or loop)");
        System.out.println("  3. Safepoint reached in method");
        System.out.println("  4. Stack frame replaced with compiled version");
        System.out.println("  5. Execution continues in compiled code");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - OSR is more complex than standard compilation");
        System.out.println("  - Compiles loops, not just methods");
        System.out.println("  - Use -XX:+PrintCompilation to see OSR");
        System.out.println("  - OSR code may be less optimized than entry-point code");
    }
}
