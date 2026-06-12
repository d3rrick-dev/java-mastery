package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Bootstrap and System ClassLoader?
 *
 * Difficulty: ADVANCED
 */

public class Lesson34_WhatIsTheDifferenceBetweenBootstrapAndSystemClassLoader {
    public static void main(String[] args) {
        System.out.println("=== BOOTSTRAP VS SYSTEM CLASSLOADER ===\n");
        System.out.println("Bootstrap ClassLoader (Primordial):");
        System.out.println("  - Top-level class loader in hierarchy");
        System.out.println("  - Loads core Java classes (java.lang, java.util, etc.)");
        System.out.println("  - Implemented in native code (C/C++)");
        System.out.println("  - Loads from JRE/lib/rt.jar or modules");
        System.out.println("  - Parent of all other class loaders");
        System.out.println("  - null parent (no parent)");
        System.out.println();
        System.out.println("System ClassLoader (Application):");
        System.out.println("  - Loads classes from CLASSPATH");
        System.out.println("  - Child of Extension ClassLoader");
        System.out.println("  - Loads application-specific classes");
        System.out.println("  - Implemented in Java");
        System.out.println("  - Can be obtained: ClassLoader.getSystemClassLoader()");
        System.out.println();
        System.out.println("ClassLoader Hierarchy:");
        System.out.println("  Bootstrap (null) -> Extension -> System -> Custom");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - Can Bootstrap class loader load classes from CLASSPATH?");
        System.out.println("  - Answer: No, it only loads core Java classes");
    }
}
