package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between PATH and CLASSPATH?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson19_WhatIsTheDifferenceBetweenPathAndClasspath {
    public static void main(String[] args) {
        System.out.println("=== PATH VS CLASSPATH ===\n");
        System.out.println("PATH:");
        System.out.println("  - Operating system environment variable");
        System.out.println("  - Specifies where to find executable programs (java, javac)");
        System.out.println("  - Used by OS to locate commands");
        System.out.println("  - Example: /usr/bin:/usr/local/bin");
        System.out.println();
        System.out.println("CLASSPATH:");
        System.out.println("  - Java-specific environment variable");
        System.out.println("  - Specifies where to find class files and libraries");
        System.out.println("  - Used by JVM to load classes");
        System.out.println("  - Example: .:lib/*:classes/");
        System.out.println();
        System.out.println("PATH is for OS executables, CLASSPATH is for Java classes");
    }
}
