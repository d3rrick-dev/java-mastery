package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle UnsupportedClassVersionError?
 *
 * Difficulty: SENIOR
 */

public class Lesson12_HowDoesTheJVMHandleUnsupportedClassVersionError {
    public static void main(String[] args) {
        System.out.println("=== UNSUPPORTEDCLASSVERSIONERROR ===\n");
        System.out.println("What is UnsupportedClassVersionError?");
        System.out.println("  - Thrown when JVM cannot read .class file version");
        System.out.println("  - Class compiled with newer JDK than runtime JVM");
        System.out.println("  - Extends ClassFormatError");
        System.out.println();
        System.out.println("Class File Version:");
        System.out.println("  - Each JDK has specific class file version");
        System.out.println("  - Java 8: 52.0");
        System.out.println("  - Java 9: 53.0");
        System.out.println("  - Java 10: 54.0");
        System.out.println("  - Java 11: 55.0");
        System.out.println("  - Java 17: 61.0");
        System.out.println();
        System.out.println("Common Scenario:");
        System.out.println("  - Compile with JDK 11 (version 55)");
        System.out.println("  - Run with JRE 8 (supports up to 52)");
        System.out.println("  - JVM throws UnsupportedClassVersionError");
        System.out.println();
        System.out.println("Solution:");
        System.out.println("  - Use same JDK version for compile and runtime");
        System.out.println("  - Or use -target and -source flags");
        System.out.println("  - Or use Maven/Gradle compiler plugin");
        System.out.println("  - Or upgrade runtime JVM");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Common in CI/CD pipelines with version mismatches");
        System.out.println("  - Docker images may have different JVM versions");
        System.out.println("  - Use jdeps to check dependencies");
        System.out.println("  - Use Maven Enforcer plugin to enforce versions");
    }
}
