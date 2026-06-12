package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle InstantiationError?
 *
 * Difficulty: SENIOR
 */

public class Lesson15_HowDoesTheJVMHandleInstantiationError {
    public static void main(String[] args) {
        System.out.println("=== INSTANTIATIONERROR HANDLING ===\n");
        System.out.println("What is InstantiationError?");
        System.out.println("  - Thrown when application tries to use new() on abstract class or interface");
        System.out.println("  - Indicates attempt to instantiate non-concrete class");
        System.out.println("  - Extends IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - new AbstractClass()");
        System.out.println("  - new Interface()");
        System.out.println("  - Binary incompatibility (class became abstract)");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { } // concrete");
        System.out.println("  - Runtime: abstract class A { } // changed to abstract");
        System.out.println("  - Code: new A() // throws InstantiationError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Don't change concrete classes to abstract");
        System.out.println("  - Use factory pattern for object creation");
        System.out.println("  - Maintain API stability");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Often caused by library updates");
        System.out.println("  - Can affect reflection-based code");
        System.out.println("  - Use dependency injection to avoid direct instantiation");
    }
}
