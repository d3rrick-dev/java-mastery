package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT deoptimization?
 *
 * Difficulty: SENIOR
 */

public class Lesson43_HowDoesTheJVMHandleJITDeoptimization {
    public static void main(String[] args) {
        System.out.println("=== JIT DEOPTIMIZATION IN JVM ===\n");
        System.out.println("What is Deoptimization?");
        System.out.println("  - Reverting compiled code back to interpreter");
        System.out.println("  - Happens when assumptions are violated");
        System.out.println("  - Also called 'uncommon trap' or 'deopt'");
        System.out.println();
        System.out.println("When it Happens:");
        System.out.println("  - Class hierarchy changes (new subclass loaded)");
        System.out.println("  - Inline cache becomes megamorphic");
        System.out.println("  - Profile data changes significantly");
        System.out.println("  - Code becomes invalid (OSR, etc.)");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  1. JIT detects assumption violation");
        System.out.println("  2. Safepoint reached");
        System.out.println("  3. Stack unwound to interpreter state");
        System.out.println("  4. Execution continues in interpreter");
        System.out.println("  5. Method may be recompiled later");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Deoptimization is expensive");
        System.out.println("  - Use -XX:+PrintDeoptimization to see");
        System.out.println("  - Can indicate design issues (too many classes)");
        System.out.println("  - Tiered compilation helps recover faster");
    }
}
