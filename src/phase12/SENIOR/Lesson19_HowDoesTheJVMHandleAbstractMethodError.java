package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle AbstractMethodError?
 *
 * Difficulty: SENIOR
 */

public class Lesson19_HowDoesTheJVMHandleAbstractMethodError {
    public static void main(String[] args) {
        System.out.println("=== ABSTRACTMETHODERROR HANDLING ===\n");
        System.out.println("What is AbstractMethodError?");
        System.out.println("  - Thrown when application tries to call abstract method");
        System.out.println("  - Method was concrete at compile time but abstract at runtime");
        System.out.println("  - Extends IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Method was changed to abstract");
        System.out.println("  - Class hierarchy changed");
        System.out.println("  - Binary incompatibility");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { void method() {} }");
        System.out.println("  - Runtime: abstract class A { abstract void method(); }");
        System.out.println("  - Code: a.method() // throws AbstractMethodError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Don't change concrete methods to abstract");
        System.out.println("  - Use interfaces for contracts");
        System.out.println("  - Maintain API stability");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Common in framework updates");
        System.out.println("  - Can affect proxy-based code (Spring, etc.)");
        System.out.println("  - Use abstract classes carefully in APIs");
    }
}
