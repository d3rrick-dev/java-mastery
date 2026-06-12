package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between JIT and JVM?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson23_WhatIsTheDifferenceBetweenJITAndJVM {
    public static void main(String[] args) {
        System.out.println("=== JIT VS JVM ===\n");
        System.out.println("JVM (Java Virtual Machine):");
        System.out.println("  - Abstract machine that runs Java bytecode");
        System.out.println("  - Interprets bytecode line by line");
        System.out.println("  - Platform-dependent implementation");
        System.out.println("  - Manages memory, class loading, security");
        System.out.println();
        System.out.println("JIT (Just-In-Time Compiler):");
        System.out.println("  - Part of JVM");
        System.out.println("  - Compiles bytecode to native machine code at runtime");
        System.out.println("  - Improves performance by caching compiled code");
        System.out.println("  - HotSpot: compiles frequently executed methods");
        System.out.println();
        System.out.println("JVM interprets, JIT compiles for performance");
    }
}
