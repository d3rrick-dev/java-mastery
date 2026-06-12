package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle NoSuchMethodError?
 *
 * Difficulty: SENIOR
 */

public class Lesson18_HowDoesTheJVMHandleNoSuchMethodError {
    public static void main(String[] args) {
        System.out.println("=== NOSUCHMETHODERROR HANDLING ===\n");
        System.out.println("What is NoSuchMethodError?");
        System.out.println("  - Thrown when application tries to call non-existent method");
        System.out.println("  - Method existed at compile time but not at runtime");
        System.out.println("  - Extends IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Method was removed from class");
        System.out.println("  - Method signature changed (parameters, return type)");
        System.out.println("  - Binary incompatibility");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { void method(int x) {} }");
        System.out.println("  - Runtime: class A { void method(String x) {} } // signature changed");
        System.out.println("  - Code: a.method(10) // throws NoSuchMethodError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Don't change method signatures");
        System.out.println("  - Overload instead of changing signature");
        System.out.println("  - Deprecate before removal");
        System.out.println("  - Use interfaces for contracts");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Common in library/framework updates");
        System.out.println("  - Can affect reflection-based code");
        System.out.println("  - Use method handles for safer reflection");
    }
}
