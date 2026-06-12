package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle NoClassDefFoundError?
 *
 * Difficulty: SENIOR
 */

public class Lesson11_HowDoesTheJVMHandleNoClassDefFoundError {
    public static void main(String[] args) {
        System.out.println("=== NOCLASSDEFFOUNDERROR HANDLING ===\n");
        System.out.println("What is NoClassDefFoundError?");
        System.out.println("  - Thrown when class was present during compilation");
        System.out.println("  - But NOT found during runtime");
        System.out.println("  - Extends LinkageError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Missing JAR in classpath");
        System.out.println("  - Class not in classpath");
        System.out.println("  - Static initializer threw exception");
        System.out.println("  - Class was removed after compilation");
        System.out.println();
        System.out.println("Difference from ClassNotFoundException:");
        System.out.println("  - NoClassDefFoundError: class was available at compile time");
        System.out.println("  - ClassNotFoundException: class loaded dynamically (Class.forName)");
        System.out.println("  - NoClassDefFoundError: Error (unchecked)");
        System.out.println("  - ClassNotFoundException: Exception (checked)");
        System.out.println();
        System.out.println("Diagnosis:");
        System.out.println("  - Check classpath: java -cp ...");
        System.out.println("  - Check JAR dependencies");
        System.out.println("  - Check for static initializer failures");
        System.out.println("  - Use verbose class loading: -verbose:class");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Often caused by dependency conflicts");
        System.out.println("  - Can be caused by classloader issues in containers");
        System.out.println("  - Maven/Gradle can help manage dependencies");
        System.out.println("  - Use dependency tree analysis: mvn dependency:tree");
    }
}
