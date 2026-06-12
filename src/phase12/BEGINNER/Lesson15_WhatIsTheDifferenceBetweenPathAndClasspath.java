package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between PATH and CLASSPATH?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of Java environment
 * - Shows knowledge of how Java finds classes
 * - Reveals awareness of development setup
 */

public class Lesson15_WhatIsTheDifferenceBetweenPathAndClasspath {

    public static void main(String[] args) {
        System.out.println("=== PATH VS CLASSPATH ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between PATH and CLASSPATH?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("PATH:");
        System.out.println("  - Operating system environment variable");
        System.out.println("  - Used to locate EXECUTABLES (java, javac, etc.)");
        System.out.println("  - Set by OS, used by OS");
        System.out.println("  - Example: C:\\Program Files\\Java\\jdk-11\\bin");
        System.out.println();

        System.out.println("CLASSPATH:");
        System.out.println("  - Java-specific environment variable");
        System.out.println("  - Used to locate CLASS FILES and JARs");
        System.out.println("  - Set by developer, used by JVM");
        System.out.println("  - Example: .;C:\\libs\\myapp.jar");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. PATH: OS level, for executables");
        System.out.println("  2. CLASSPATH: JVM level, for classes");
        System.out.println("  3. PATH: Required for java/javac commands");
        System.out.println("  4. CLASSPATH: Required for class loading");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Setting PATH (Windows):");
        System.out.println("  set PATH=%PATH%;C:\\Program Files\\Java\\jdk-11\\bin");
        System.out.println();
        System.out.println("Setting PATH (Linux/Mac):");
        System.out.println("  export PATH=$PATH:/usr/lib/jvm/java-11/bin");
        System.out.println();
        System.out.println("Setting CLASSPATH:");
        System.out.println("  java -cp .;myapp.jar com.example.Main");
        System.out.println("  // -cp or -classpath specifies where to find classes");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Confusing the two:");
        System.out.println("   - PATH is for OS executables");
        System.out.println("   - CLASSPATH is for Java classes");
        System.out.println();

        System.out.println("2. Not setting CLASSPATH:");
        System.out.println("   - Default is current directory (.)");
        System.out.println("   - Use -cp flag for explicit control");
        System.out.println();

        System.out.println("3. Wrong CLASSPATH separator:");
        System.out.println("   - Windows: semicolon (;)");
        System.out.println("   - Linux/Mac: colon (:)");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What happens if PATH is not set?");
        System.out.println("2. What is the default CLASSPATH?");
        System.out.println("3. How does the JVM find classes?");
        System.out.println("4. What is the bootstrap classpath?");
        System.out.println("5. How do you set CLASSPATH in an IDE?");
    }
}
