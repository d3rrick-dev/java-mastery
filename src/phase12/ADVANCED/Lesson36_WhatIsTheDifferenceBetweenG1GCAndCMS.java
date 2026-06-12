package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between G1GC and CMS garbage collectors?
 *
 * Difficulty: ADVANCED
 */

public class Lesson36_WhatIsTheDifferenceBetweenG1GCAndCMS {
    public static void main(String[] args) {
        System.out.println("=== G1GC VS CMS GARBAGE COLLECTORS ===\n");
        System.out.println("CMS (Concurrent Mark Sweep):");
        System.out.println("  - Low-pause collector (concurrent)");
        System.out.println("  - Uses multiple threads for marking and sweeping");
        System.out.println("  - Stop-the-world phases: initial mark, remark");
        System.out.println("  - Fragmentation issues (no compaction)");
        System.out.println("  - Deprecated in Java 9, removed in Java 14");
        System.out.println("  - -XX:+UseConcMarkSweepGC");
        System.out.println();
        System.out.println("G1GC (Garbage First):");
        System.out.println("  - Server-style collector for large heaps (4GB+)");
        System.out.println("  - Divides heap into regions (1MB-32MB)");
        System.out.println("  - Predictable pause times (target: -XX:MaxGCPauseMillis)");
        System.out.println("  - Compacts during collection (no fragmentation)");
        System.out.println("  - Default in Java 9+");
        System.out.println("  - -XX:+UseG1GC");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Fragmentation: CMS has it, G1GC doesn't");
        System.out.println("  - Pause predictability: CMS unpredictable, G1GC predictable");
        System.out.println("  - Heap size: CMS for small, G1GC for large heaps");
        System.out.println("  - Status: CMS deprecated, G1GC is default");
    }
}
