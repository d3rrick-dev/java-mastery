package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle JIT code cache?
 *
 * Difficulty: SENIOR
 */

public class Lesson44_HowDoesTheJVMHandleJITCodeCache {
    public static void main(String[] args) {
        System.out.println("=== JIT CODE CACHE IN JVM ===\n");
        System.out.println("What is Code Cache?");
        System.out.println("  - Memory area for compiled native code");
        System.out.println("  - Stores JIT-compiled methods");
        System.out.println("  - Fixed size, can fill up");
        System.out.println();
        System.out.println("Code Cache Issues:");
        System.out.println("  - CodeCacheFull: compilation stops");
        System.out.println("  - CodeCache is full, consider -XX:ReservedCodeCacheSize");
        System.out.println("  - Can cause performance degradation");
        System.out.println();
        System.out.println("How to Monitor:");
        System.out.println("  - jstat -compiler <pid>");
        System.out.println("  - jcmd <pid> Compiler.codecache");
        System.out.println("  - -XX:+PrintCodeCache");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Default size: 240MB (Java 8+)");
        System.out.println("  - Increase for large applications: -XX:ReservedCodeCacheSize=512m");
        System.out.println("  - Code cache invalidation on class redefinition");
        System.out.println("  - JVM flags: -XX:+UseCodeCacheFlushing");
    }
}
