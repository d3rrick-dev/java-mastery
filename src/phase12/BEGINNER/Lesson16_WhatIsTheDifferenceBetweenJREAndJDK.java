package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between JRE and JDK?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of Java platform
 * - Shows knowledge of development tools
 * - Reveals awareness of Java ecosystem
 */

public class Lesson16_WhatIsTheDifferenceBetweenJREAndJDK {

    public static void main(String[] args) {
        System.out.println("=== JRE VS JDK ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between JRE and JDK?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("JRE (Java Runtime Environment):");
        System.out.println("  - For RUNNING Java applications");
        System.out.println("  - Contains JVM + core libraries");
        System.out.println("  - Does NOT contain development tools");
        System.out.println("  - End users need JRE");
        System.out.println();

        System.out.println("JDK (Java Development Kit):");
        System.out.println("  - For DEVELOPING Java applications");
        System.out.println("  - Contains JRE + development tools");
        System.out.println("  - Includes: javac, java, jar, javadoc, etc.");
        System.out.println("  - Developers need JDK");
        System.out.println();

        System.out.println("JVM (Java Virtual Machine):");
        System.out.println("  - Part of JRE");
        System.out.println("  - Executes bytecode");
        System.out.println("  - Platform-specific implementation");
        System.out.println();

        // ============================================================
        // VISUAL REPRESENTATION
        // ============================================================
        System.out.println("--- Hierarchy ---\n");

        System.out.println("  JDK (Development Kit)");
        System.out.println("    |");
        System.out.println("    +-- JRE (Runtime Environment)");
        System.out.println("    |     |");
        System.out.println("    |     +-- JVM (Virtual Machine)");
        System.out.println("    |     +-- Core Libraries (java.base, etc.)");
        System.out.println("    |");
        System.out.println("    +-- Development Tools");
        System.out.println("          |-- javac (compiler)");
        System.out.println("          |-- java (launcher)");
        System.out.println("          |-- jar (archiver)");
        System.out.println("          |-- javadoc (documentation)");
        System.out.println("          |-- jdb (debugger)");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Thinking JRE is enough for development:");
        System.out.println("   - JRE can only RUN programs");
        System.out.println("   - Need JDK to COMPILE programs");
        System.out.println();

        System.out.println("2. Confusing JVM with JRE:");
        System.out.println("   - JVM is part of JRE");
        System.out.println("   - JRE = JVM + libraries");
        System.out.println();

        System.out.println("3. Not knowing JDK includes JRE:");
        System.out.println("   - Installing JDK gives you JRE too");
        System.out.println("   - No need to install JRE separately if you have JDK");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is JVM?");
        System.out.println("2. What is the difference between JVM and JRE?");
        System.out.println("3. What tools are in JDK?");
        System.out.println("4. What is JIT compiler?");
        System.out.println("5. What is the difference between JDK and JRE in terms of installation?");
    }
}
