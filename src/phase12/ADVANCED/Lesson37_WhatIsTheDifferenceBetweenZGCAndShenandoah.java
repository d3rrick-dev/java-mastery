package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between ZGC and Shenandoah garbage collectors?
 *
 * Difficulty: ADVANCED
 */

public class Lesson37_WhatIsTheDifferenceBetweenZGCAndShenandoah {
    public static void main(String[] args) {
        System.out.println("=== ZGC VS SHENANDOAH GARBAGE COLLECTORS ===\n");
        System.out.println("ZGC (Z Garbage Collector):");
        System.out.println("  - Low-latency, scalable GC (Java 11+)");
        System.out.println("  - Pause times < 1ms regardless of heap size");
        System.out.println("  - Uses colored pointers and load barriers");
        System.out.println("  - Concurrent: most work done while application runs");
        System.out.println("  - Region-based heap (similar to G1GC)");
        System.out.println("  - -XX:+UseZGC");
        System.out.println();
        System.out.println("Shenandoah:");
        System.out.println("  - Low-latency GC (Red Hat, Java 12+)");
        System.out.println("  - Pause times independent of heap size");
        System.out.println("  - Concurrent evacuation (compaction)");
        System.out.println("  - Uses Brooks pointers (forwarding pointers)");
        System.out.println("  - Region-based heap");
        System.out.println("  - -XX:+UseShenandoahGC");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - ZGC: colored pointers, load barriers");
        System.out.println("  - Shenandoah: Brooks pointers, evacuation");
        System.out.println("  - Both: sub-millisecond pauses, concurrent");
        System.out.println("  - ZGC: OpenJDK, Shenandoah: Red Hat");
        System.out.println("  - ZGC: Linux only (Java 11-16), cross-platform (Java 17+)");
    }
}
