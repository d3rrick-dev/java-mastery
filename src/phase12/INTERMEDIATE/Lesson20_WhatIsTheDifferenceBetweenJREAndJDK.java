package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between JRE and JDK?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson20_WhatIsTheDifferenceBetweenJREAndJDK {
    public static void main(String[] args) {
        System.out.println("=== JRE VS JDK ===\n");
        System.out.println("JRE (Java Runtime Environment):");
        System.out.println("  - For running Java applications");
        System.out.println("  - Contains JVM + core libraries");
        System.out.println("  - Does NOT contain development tools");
        System.out.println("  - End users need JRE");
        System.out.println();
        System.out.println("JDK (Java Development Kit):");
        System.out.println("  - For developing Java applications");
        System.out.println("  - Contains JRE + development tools (javac, java, jar)");
        System.out.println("  - Used by developers");
        System.out.println("  - Includes debugger, compiler, etc.");
        System.out.println();
        System.out.println("JDK = JRE + Development Tools");
    }
}
