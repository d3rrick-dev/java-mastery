package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle class circularity errors?
 *
 * Difficulty: SENIOR
 */

public class Lesson08_HowDoesTheJVMHandleClassCircularityError {
    public static void main(String[] args) {
        System.out.println("=== CLASS CIRCULARITY ERROR ===\n");
        System.out.println("What is ClassCircularityError?");
        System.out.println("  - Thrown when class initialization depends on itself");
        System.out.println("  - Circular dependency during class initialization");
        System.out.println("  - Extends LinkageError");
        System.out.println();
        System.out.println("Example Scenario:");
        System.out.println("  - Class A has static field initialized with new B()");
        System.out.println("  - Class B has static field initialized with new A()");
        System.out.println("  - A -> B -> A: circular dependency");
        System.out.println("  - JVM detects and throws ClassCircularityError");
        System.out.println();
        System.out.println("How JVM Detects:");
        System.out.println("  - During initialization phase");
        System.out.println("  - Tracks classes being initialized");
        System.out.println("  - If class already in initialization, throws error");
        System.out.println("  - Prevents infinite recursion");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Avoid circular static dependencies");
        System.out.println("  - Use lazy initialization");
        System.out.println("  - Use dependency injection");
        System.out.println("  - Break circular references");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Happens at class loading time, not runtime");
        System.out.println("  - Different from StackOverflowError (runtime)");
        System.out.println("  - Common in frameworks with auto-wiring");
        System.out.println("  - Can be caught and logged for diagnostics");
    }
}
