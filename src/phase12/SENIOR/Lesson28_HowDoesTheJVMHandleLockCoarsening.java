package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle lock coarsening?
 *
 * Difficulty: SENIOR
 */

public class Lesson28_HowDoesTheJVMHandleLockCoarsening {
    public static void main(String[] args) {
        System.out.println("=== LOCK COARSENING IN JVM ===\n");
        System.out.println("What is Lock Coarsening?");
        System.out.println("  - JIT optimization to reduce lock overhead");
        System.out.println("  - Merges adjacent synchronized blocks with same lock");
        System.out.println("  - Reduces number of lock acquisitions");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - synchronized void method() {");
        System.out.println("      // operation 1");
        System.out.println("      // operation 2");
        System.out.println("      // operation 3");
        System.out.println("    }");
        System.out.println("  - JVM may coarsen to single lock for entire method");
        System.out.println();
        System.out.println("When it Helps:");
        System.out.println("  - Multiple small synchronized blocks");
        System.out.println("  - Same lock object used repeatedly");
        System.out.println("  - Reduces CAS operations");
        System.out.println();
        System.out.println("When it Hurts:");
        System.out.println("  - Increases lock hold time");
        System.out.println("  - Reduces concurrency");
        System.out.println("  - May cause more contention");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Automatic optimization by JIT");
        System.out.println("  - Can be disabled with -XX:-DoLockCoarsening");
        System.out.println("  - Manual coarsening sometimes better for fine control");
    }
}
