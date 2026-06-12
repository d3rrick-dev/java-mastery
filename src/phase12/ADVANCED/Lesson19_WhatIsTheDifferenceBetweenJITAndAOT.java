package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between JIT and AOT compilation?
 *
 * Difficulty: ADVANCED
 */

public class Lesson19_WhatIsTheDifferenceBetweenJITAndAOT {
    public static void main(String[] args) {
        System.out.println("=== JIT VS AOT COMPILATION ===\n");
        System.out.println("JIT (Just-In-Time) Compilation:");
        System.out.println("  - Compiles bytecode to native code at runtime");
        System.out.println("  - Happens during program execution");
        System.out.println("  - Optimizes based on actual usage patterns");
        System.out.println("  - Used by HotSpot JVM (default)");
        System.out.println("  - Pros: adaptive optimization, profile-guided");
        System.out.println("  - Cons: startup overhead, warmup time");
        System.out.println();
        System.out.println("AOT (Ahead-Of-Time) Compilation:");
        System.out.println("  - Compiles bytecode to native code before execution");
        System.out.println("  - Happens during build/deployment");
        System.out.println("  - No runtime compilation overhead");
        System.out.println("  - Used by GraalVM native-image, Excelsior JET");
        System.out.println("  - Pros: fast startup, no warmup, smaller memory");
        System.out.println("  - Cons: no runtime optimization, larger binaries");
        System.out.println();
        System.out.println("Java 9+ AOT:");
        System.out.println("  - jaotc tool compiles classes to native code");
        System.out.println("  - Stored in shared library/cache");
        System.out.println("  - Reduces startup time for large applications");
    }
}
