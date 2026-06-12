package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between JDK, JRE, and JVM?
 *
 * Difficulty: ADVANCED
 */

public class Lesson18_WhatIsTheDifferenceBetweenJDKJREAndJVM {
    public static void main(String[] args) {
        System.out.println("=== JDK, JRE, AND JVM ===\n");
        System.out.println("JVM (Java Virtual Machine):");
        System.out.println("  - Abstract machine that runs Java bytecode");
        System.out.println("  - Platform-dependent (different for Windows, Mac, Linux)");
        System.out.println("  - Responsible for loading, verifying, executing bytecode");
        System.out.println("  - Includes class loader, memory areas, execution engine");
        System.out.println();
        System.out.println("JRE (Java Runtime Environment):");
        System.out.println("  - JVM + core libraries (rt.jar)");
        System.out.println("  - Needed to RUN Java applications");
        System.out.println("  - Does NOT include development tools");
        System.out.println("  - End users need JRE");
        System.out.println();
        System.out.println("JDK (Java Development Kit):");
        System.out.println("  - JRE + development tools (javac, java, jar, etc.)");
        System.out.println("  - Needed to DEVELOP Java applications");
        System.out.println("  - Includes compiler, debugger, documentation tools");
        System.out.println("  - Developers need JDK");
        System.out.println();
        System.out.println("Hierarchy:");
        System.out.println("  JDK = JRE + Development Tools");
        System.out.println("  JRE = JVM + Core Libraries");
    }
}
