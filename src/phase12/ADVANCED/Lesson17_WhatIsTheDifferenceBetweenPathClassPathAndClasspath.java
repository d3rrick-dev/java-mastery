package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between PATH, CLASSPATH, and classpath?
 *
 * Difficulty: ADVANCED
 */

public class Lesson17_WhatIsTheDifferenceBetweenPathClassPathAndClasspath {
    public static void main(String[] args) {
        System.out.println("=== PATH, CLASSPATH, AND CLASSPATH ===\n");
        System.out.println("PATH:");
        System.out.println("  - Operating system environment variable");
        System.out.println("  - Specifies directories for executable programs");
        System.out.println("  - Used by OS to find commands like java, javac");
        System.out.println("  - Example: /usr/bin:/usr/local/bin");
        System.out.println();
        System.out.println("CLASSPATH (environment variable):");
        System.out.println("  - Operating system environment variable");
        System.out.println("  - Specifies locations of .class files and JARs");
        System.out.println("  - Used by JVM to find classes at runtime");
        System.out.println("  - Can be set via -cp or -classpath flag");
        System.out.println();
        System.out.println("classpath (command-line argument):");
        System.out.println("  - JVM command-line argument");
        System.out.println("  - Overrides CLASSPATH environment variable");
        System.out.println("  - Example: java -cp .:lib/* MyClass");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - PATH: for OS executables");
        System.out.println("  - CLASSPATH: for JVM class loading");
        System.out.println("  - Command-line -cp overrides environment variable");
    }
}
