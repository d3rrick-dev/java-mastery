package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle deoptimization?
 *
 * Difficulty: SENIOR
 */

public class Lesson30_HowDoesTheJVMHandleDeoptimization {
    public static void main(String[] args) {
        System.out.println("=== DEOPTIMIZATION IN JVM ===\n");
        System.out.println("What is Deoptimization?");
        System.out.println("  - Process of reverting compiled code to interpreter");
        System.out.println("  - Happens when assumptions made by JIT are invalid");
        System.out.println("  - Also called 'uncommon trap' or 'safepoint poll'");
        System.out.println();
        System.out.println("When it Happens:");
        System.out.println("  - Class hierarchy changes (loaded new class)");
        System.out.println("  - Profile data invalidated");
        System.out.println("  - OSR (On-Stack Replacement) needed");
        System.out.println("  - Code cache full");
        System.out.println();
        System.out.println("Types:");
        System.out.println("  - Uncommon trap: method was compiled but rarely executed");
        System.out.println("  - Class loading: new class breaks assumptions");
        System.out.println("  - OSR: switch from compiled to interpreter mid-execution");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Deoptimization has performance cost");
        System.out.println("  - Use -XX:+PrintDeoptimization to see when it happens");
        System.out.println("  - Common in frameworks with dynamic class loading");
        System.out.println("  - Can cause performance cliffs");
    }
}
