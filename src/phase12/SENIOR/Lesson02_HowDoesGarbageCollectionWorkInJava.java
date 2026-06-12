package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does garbage collection work in Java?
 *
 * Difficulty: SENIOR
 */

public class Lesson02_HowDoesGarbageCollectionWorkInJava {
    public static void main(String[] args) {
        System.out.println("=== GARBAGE COLLECTION IN JAVA ===\n");
        System.out.println("GC Fundamentals:");
        System.out.println("  - Automatic memory management");
        System.out.println("  - Identifies and removes unreachable objects");
        System.out.println("  - Runs on heap memory");
        System.out.println("  - Different algorithms: Mark-Sweep, Mark-Compact, Copying");
        System.out.println();
        System.out.println("GC Process:");
        System.out.println("  1. Mark: identify reachable objects from GC roots");
        System.out.println("  2. Sweep: remove unreachable objects");
        System.out.println("  3. Compact: defragment heap (optional)");
        System.out.println();
        System.out.println("GC Roots:");
        System.out.println("  - Local variables and method parameters (stack)");
        System.out.println("  - Active threads");
        System.out.println("  - Static variables (classes)");
        System.out.println("  - JNI references");
        System.out.println();
        System.out.println("Generational GC:");
        System.out.println("  - Young Gen: Eden + Survivor (Minor GC)");
        System.out.println("  - Old Gen: long-lived objects (Major GC)");
        System.out.println("  - Based on weak generational hypothesis");
        System.out.println("  - Most objects die young");
        System.out.println();
        System.out.println("Senior-Level Topics:");
        System.out.println("  - GC tuning parameters");
        System.out.println("  - GC logging and analysis");
        System.out.println("  - GC overhead limits");
        System.out.println("  - Concurrent vs stop-the-world collectors");
        System.out.println("  - Region-based collectors (G1GC, ZGC, Shenandoah)");
    }
}
