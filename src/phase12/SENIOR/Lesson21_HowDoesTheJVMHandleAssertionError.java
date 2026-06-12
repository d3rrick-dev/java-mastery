package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle AssertionError?
 *
 * Difficulty: SENIOR
 */

public class Lesson21_HowDoesTheJVMHandleAssertionError {
    public static void main(String[] args) {
        System.out.println("=== ASSERTIONERROR HANDLING ===\n");
        System.out.println("What is AssertionError?");
        System.out.println("  - Thrown when assertion fails");
        System.out.println("  - Used for debugging and testing");
        System.out.println("  - Extends Error (not Exception)");
        System.out.println();
        System.out.println("Assertion Syntax:");
        System.out.println("  - assert condition;");
        System.out.println("  - assert condition : message;");
        System.out.println("  - Disabled by default at runtime");
        System.out.println();
        System.out.println("Enabling Assertions:");
        System.out.println("  - java -ea MyClass (enable)");
        System.out.println("  - java -enableassertions MyClass");
        System.out.println("  - java -da MyClass (disable, default)");
        System.out.println("  - Can enable/disable per class or package");
        System.out.println();
        System.out.println("JVM Behavior:");
        System.out.println("  - If disabled: assertions are ignored");
        System.out.println("  - If enabled: throws AssertionError if false");
        System.out.println("  - Error is thrown in the current thread");
        System.out.println("  - Can be caught (but usually not recommended)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Assertions for internal invariants, not public API");
        System.out.println("  - Don't use for argument validation (use exceptions)");
        System.out.println("  - Useful for design-by-contract programming");
        System.out.println("  - Can be used in testing frameworks");
    }
}
