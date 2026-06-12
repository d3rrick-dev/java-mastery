package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between JVM and JRE?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson21_WhatIsTheDifferenceBetweenJVMAndJRE {
    public static void main(String[] args) {
        System.out.println("=== JVM VS JRE ===\n");
        System.out.println("JVM (Java Virtual Machine):");
        System.out.println("  - Abstract machine that runs Java bytecode");
        System.out.println("  - Platform-dependent (different for Windows, Mac, Linux)");
        System.out.println("  - Responsible for loading, verifying, executing bytecode");
        System.out.println("  - Includes class loader, memory management, execution engine");
        System.out.println();
        System.out.println("JRE (Java Runtime Environment):");
        System.out.println("  - Contains JVM + core libraries");
        System.out.println("  - For running Java applications");
        System.out.println("  - Does NOT include development tools");
        System.out.println();
        System.out.println("JRE = JVM + Libraries");
        System.out.println("JDK = JRE + Development Tools");
    }
}
