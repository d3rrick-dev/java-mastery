package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle LinkageError?
 *
 * Difficulty: SENIOR
 */

public class Lesson13_HowDoesTheJVMHandleLinkageError {
    public static void main(String[] args) {
        System.out.println("=== LINKAGEERROR HANDLING ===\n");
        System.out.println("What is LinkageError?");
        System.out.println("  - Superclass for errors related to class dependencies");
        System.out.println("  - Indicates class loading/linking problem");
        System.out.println("  - Subclasses: NoClassDefFoundError, ClassFormatError, etc.");
        System.out.println();
        System.out.println("Common LinkageErrors:");
        System.out.println("  - NoClassDefFoundError: class not found at runtime");
        System.out.println("  - ClassFormatError: malformed class file");
        System.out.println("  - UnsupportedClassVersionError: version mismatch");
        System.out.println("  - ClassCircularityError: circular dependency");
        System.out.println("  - IncompatibleClassChangeError: binary incompatibility");
        System.out.println();
        System.out.println("JVM Behavior:");
        System.out.println("  - Thrown during class loading/linking");
        System.out.println("  - Prevents class from being loaded");
        System.out.println("  - Application may fail to start");
        System.out.println("  - Error is thrown in the thread that triggered loading");
        System.out.println();
        System.out.println("Diagnosis:");
        System.out.println("  - Check classpath and dependencies");
        System.out.println("  - Verify class file integrity");
        System.out.println("  - Check for version compatibility");
        System.out.println("  - Use -verbose:class to trace loading");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - LinkageErrors are often deployment issues");
        System.out.println("  - Can be caused by OSGi/classloader conflicts");
        System.out.println("  - Common in microservices with shared libraries");
        System.out.println("  - Use dependency management tools to prevent");
    }
}
