package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between JDK and JRE?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson22_WhatIsTheDifferenceBetweenJDKAndJRE {
    public static void main(String[] args) {
        System.out.println("=== JDK VS JRE ===\n");
        System.out.println("JDK (Java Development Kit):");
        System.out.println("  - For developers");
        System.out.println("  - Contains JRE + development tools");
        System.out.println("  - Includes: javac, java, jar, javadoc, jdb");
        System.out.println("  - Used to compile and develop Java applications");
        System.out.println();
        System.out.println("JRE (Java Runtime Environment):");
        System.out.println("  - For end users");
        System.out.println("  - Contains JVM + core libraries");
        System.out.println("  - Used to run Java applications");
        System.out.println("  - Does NOT include development tools");
        System.out.println();
        System.out.println("JDK = JRE + Development Tools");
        System.out.println("JRE = JVM + Libraries");
    }
}
