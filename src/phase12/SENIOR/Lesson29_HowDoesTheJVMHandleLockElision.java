package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle lock elision?
 *
 * Difficulty: SENIOR
 */

public class Lesson29_HowDoesTheJVMHandleLockElision {
    public static void main(String[] args) {
        System.out.println("=== LOCK ELISION IN JVM ===\n");
        System.out.println("What is Lock Elision?");
        System.out.println("  - JIT optimization to eliminate unnecessary locks");
        System.out.println("  - Removes locks when object doesn't escape thread");
        System.out.println("  - Enabled by escape analysis");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - synchronized block on local object");
        System.out.println("  - Object doesn't escape current thread");
        System.out.println("  - JVM determines no other thread can access");
        System.out.println("  - Lock is eliminated entirely");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - void method() {");
        System.out.println("      Object lock = new Object();");
        System.out.println("      synchronized(lock) { /* work */ }");
        System.out.println("    }");
        System.out.println("  - lock doesn't escape, so synchronization is removed");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Significant performance improvement");
        System.out.println("  - Requires escape analysis to be enabled");
        System.out.println("  - Not applicable to shared locks");
        System.out.println("  - Part of JVM's lock optimizations");
    }
}
