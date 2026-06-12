package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle IllegalAccessError?
 *
 * Difficulty: SENIOR
 */

public class Lesson16_HowDoesTheJVMHandleIllegalAccessError {
    public static void main(String[] args) {
        System.out.println("=== ILLEGALACCESSERROR HANDLING ===\n");
        System.out.println("What is IllegalAccessError?");
        System.out.println("  - Thrown when code tries to access field/method illegally");
        System.out.println("  - Access control violation at binary level");
        System.out.println("  - Extends IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Accessing private member from another class");
        System.out.println("  - Accessing package-private from different package");
        System.out.println("  - Binary incompatibility (visibility changed)");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { public int x; }");
        System.out.println("  - Runtime: class A { private int x; } // visibility changed");
        System.out.println("  - Code: a.x // throws IllegalAccessError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Don't reduce visibility of public members");
        System.out.println("  - Use interfaces for contracts");
        System.out.println("  - Maintain API stability");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Different from IllegalAccessException (reflection)");
        System.out.println("  - IllegalAccessError: binary level");
        System.out.println("  - IllegalAccessException: reflection level");
    }
}
