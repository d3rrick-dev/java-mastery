package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle ExceptionInInitializerError?
 *
 * Difficulty: SENIOR
 */

public class Lesson20_HowDoesTheJVMHandleExceptionInInitializerError {
    public static void main(String[] args) {
        System.out.println("=== EXCEPTIONININITIALIZERERROR ===\n");
        System.out.println("What is ExceptionInInitializerError?");
        System.out.println("  - Thrown when static initializer throws exception");
        System.out.println("  - Static block or static variable initialization fails");
        System.out.println("  - Extends LinkageError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Exception in static block");
        System.out.println("  - Exception initializing static variable");
        System.out.println("  - NullPointerException in static context");
        System.out.println("  - ArithmeticException in static context");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - static { int x = 10/0; } // ArithmeticException");
        System.out.println("  - static { throw new RuntimeException(); }");
        System.out.println("  - JVM wraps in ExceptionInInitializerError");
        System.out.println();
        System.out.println("Diagnosis:");
        System.out.println("  - Check static initializers");
        System.out.println("  - Check static variable initialization");
        System.out.println("  - Use getCause() to find root exception");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Class becomes unusable after this error");
        System.out.println("  - Subsequent uses throw NoClassDefFoundError");
        System.out.println("  - Common in complex static initialization");
        System.out.println("  - Use lazy initialization to avoid");
    }
}
