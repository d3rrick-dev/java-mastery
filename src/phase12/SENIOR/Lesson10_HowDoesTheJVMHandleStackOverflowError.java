package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle StackOverflowError?
 *
 * Difficulty: SENIOR
 */

public class Lesson10_HowDoesTheJVMHandleStackOverflowError {
    public static void main(String[] args) {
        System.out.println("=== STACKOVERFLOWERROR HANDLING ===\n");
        System.out.println("What is StackOverflowError?");
        System.out.println("  - Thrown when stack memory is exhausted");
        System.out.println("  - Usually caused by infinite or deep recursion");
        System.out.println("  - Extends VirtualMachineError");
        System.out.println();
        System.out.println("JVM Behavior:");
        System.out.println("  - Throws StackOverflowError in the current thread");
        System.out.println("  - Thread terminates (unless caught)");
        System.out.println("  - Other threads continue running");
        System.out.println("  - JVM does NOT terminate automatically");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Infinite recursion (no base case)");
        System.out.println("  - Very deep recursion (exceeds stack size)");
        System.out.println("  - Each method call adds stack frame");
        System.out.println("  - Default stack size: ~1MB (platform dependent)");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Use iteration instead of recursion");
        System.out.println("  - Increase stack size: -Xss2m");
        System.out.println("  - Ensure base case in recursion");
        System.out.println("  - Tail recursion (not optimized in Java)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Stack size is per-thread, not per-process");
        System.out.println("  - More threads = less stack per thread");
        System.out.println("  - StackOverflowError vs OutOfMemoryError:");
        System.out.println("    - StackOverflow: stack memory exhausted");
        System.out.println("    - OutOfMemory: heap memory exhausted");
        System.out.println("  - Can be caught but usually indicates bug");
    }
}
