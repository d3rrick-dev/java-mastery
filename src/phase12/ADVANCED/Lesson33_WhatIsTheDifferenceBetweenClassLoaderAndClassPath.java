package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between ClassLoader and ClassPath?
 *
 * Difficulty: ADVANCED
 */

public class Lesson33_WhatIsTheDifferenceBetweenClassLoaderAndClassPath {
    public static void main(String[] args) {
        System.out.println("=== CLASSLOADER VS CLASSPATH ===\n");
        System.out.println("ClassLoader:");
        System.out.println("  - Part of JVM that loads classes");
        System.out.println("  - Responsible for finding and loading .class files");
        System.out.println("  - Hierarchy: Bootstrap -> Extension -> System");
        System.out.println("  - Uses delegation model (parent-first)");
        System.out.println("  - Can be customized (custom class loaders)");
        System.out.println();
        System.out.println("ClassPath:");
        System.out.println("  - Environment variable or command-line argument");
        System.out.println("  - Specifies WHERE to look for classes");
        System.out.println("  - List of directories and JAR files");
        System.out.println("  - Used by ClassLoader to find classes");
        System.out.println("  - Example: -cp .:lib/*");
        System.out.println();
        System.out.println("Relationship:");
        System.out.println("  - ClassPath tells ClassLoader WHERE to look");
        System.out.println("  - ClassLoader uses ClassPath to FIND classes");
        System.out.println("  - ClassLoader loads classes FROM ClassPath locations");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - What is class loading delegation model?");
        System.out.println("  - Answer: Parent class loader loads first, child loads if parent fails");
    }
}
