package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle IncompatibleClassChangeError?
 *
 * Difficulty: SENIOR
 */

public class Lesson14_HowDoesTheJVMHandleIncompatibleClassChangeError {
    public static void main(String[] args) {
        System.out.println("=== INCOMPATIBLECLASSCHANGEERROR ===\n");
        System.out.println("What is IncompatibleClassChangeError?");
        System.out.println("  - Thrown when class definition changes incompatibly");
        System.out.println("  - Binary incompatible changes between compile and runtime");
        System.out.println("  - Extends LinkageError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Method signature changed");
        System.out.println("  - Field type changed");
        System.out.println("  - Class hierarchy changed (superclass, interfaces)");
        System.out.println("  - Method made non-static or vice versa");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { void method() {} }");
        System.out.println("  - Runtime: class A { int method() {} } // return type changed");
        System.out.println("  - JVM throws IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Maintain backward compatibility");
        System.out.println("  - Use semantic versioning");
        System.out.println("  - Deprecate before removing/changing");
        System.out.println("  - Use interfaces for contracts");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Common in library/framework updates");
        System.out.println("  - Can affect multiple dependent modules");
        System.out.println("  - Use maven-enforcer for dependency convergence");
        System.out.println("  - API design should minimize breaking changes");
    }
}
